import { useEffect, useRef, useState } from 'react';
import { TransformWrapper, TransformComponent } from "react-zoom-pan-pinch";
import './App.css';

const COLOR_PALETTE = [
  "#FF4500", "#FFA800", "#FFD635", "#00A368", "#7EED56",
  "#2450A4", "#3690EA", "#51E9F4", "#811E9F", "#B44AC0",
  "#FF99AA", "#9C6926", "#000000", "#898D90", "#D4D7D9", "#FFFFFF"
];

function App() {
  const canvasRef = useRef(null);
  const socketRef = useRef(null);

  // 🔥 드래그인지 클릭인지 판단하기 위한 좌표 저장소
  const mouseDownPos = useRef({ x: 0, y: 0 });

  const [isConnected, setIsConnected] = useState(false);
  const [myColor, setMyColor] = useState(COLOR_PALETTE[0]);

  const PIXEL_SIZE = 10;
  const GRID_SIZE = 50;
  const CANVAS_SIZE = GRID_SIZE * PIXEL_SIZE; // 500px

  const drawPixel = (x, y, color) => {
    const canvas = canvasRef.current;
    if (!canvas) return;
    const ctx = canvas.getContext('2d');
    ctx.fillStyle = color;
    ctx.fillRect(x * PIXEL_SIZE, y * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
  };

  useEffect(() => {
    fetch("http://localhost:8080/api/pixels")
      .then(res => res.json())
      .then(pixels => {
        pixels.forEach(p => drawPixel(p.x, p.y, p.color));
      })
      .catch(err => console.error("초기 데이터 로딩 실패:", err));

    const ws = new WebSocket("ws://localhost:8080/ws/pixel");
    socketRef.current = ws;

    ws.onopen = () => setIsConnected(true);
    ws.onmessage = (event) => {
      const data = JSON.parse(event.data);
      drawPixel(data.x, data.y, data.color);
    };
    ws.onclose = () => setIsConnected(false);

    return () => { if (ws.readyState === 1) ws.close(); };
  }, []);

  // 🖱️ 마우스 누름: 시작 위치 기억
  const handleMouseDown = (e) => {
    mouseDownPos.current = { x: e.clientX, y: e.clientY };
  };

  // 🖱️ 마우스 뗌: 위치 비교 후 색칠 여부 결정
  const handleMouseUp = (e) => {
    if (!socketRef.current || socketRef.current.readyState !== WebSocket.OPEN) return;

    // 1. 이동 거리 계산 (피타고라스까지 갈 필요도 없이 단순 차이 계산)
    const moveX = Math.abs(e.clientX - mouseDownPos.current.x);
    const moveY = Math.abs(e.clientY - mouseDownPos.current.y);

    // 🔥 핵심: 5픽셀 이상 움직였으면 "드래그(이동)"로 간주하고 색칠 안 함!
    if (moveX > 5 || moveY > 5) return;

    // --- 여기부터는 색칠 로직 ---
    const canvas = canvasRef.current;
    // zoom 라이브러리 내부 오프셋 사용
    const mouseX = e.nativeEvent.offsetX;
    const mouseY = e.nativeEvent.offsetY;

    const x = Math.floor(mouseX / PIXEL_SIZE);
    const y = Math.floor(mouseY / PIXEL_SIZE);

    if (x < 0 || x >= GRID_SIZE || y < 0 || y >= GRID_SIZE) return;

    socketRef.current.send(JSON.stringify({ x, y, color: myColor }));
    drawPixel(x, y, myColor);
  };

  return (
    <div className="app-container">
      <header className="app-header">
        <h1 className="logo">Pixel Live</h1>
        <div className={`status-indicator ${isConnected ? 'on' : 'off'}`}>
          {isConnected ? 'LIVE' : 'OFFLINE'}
        </div>
      </header>

      <TransformWrapper
        initialScale={1}
        minScale={0.5}
        maxScale={4}
        centerOnInit={true}
        wheel={{ step: 0.1 }}
        doubleClick={{ disabled: true }} // 더블클릭 확대 방지
      >
        {({ zoomIn, zoomOut, resetTransform }) => (
          <>
            <div className="zoom-controls">
              <button onClick={() => zoomIn()}>➕</button>
              <button onClick={() => zoomOut()}>➖</button>
              <button onClick={() => resetTransform()}>🔄</button>
            </div>

            <main className="canvas-container">
              <TransformComponent>
                <canvas
                  ref={canvasRef}
                  width={CANVAS_SIZE}
                  height={CANVAS_SIZE}
                  // 🔥 onClick 대신 MouseDown/Up 조합 사용
                  onMouseDown={handleMouseDown}
                  onMouseUp={handleMouseUp}
                  className="pixel-canvas"
                />
              </TransformComponent>
            </main>
          </>
        )}
      </TransformWrapper>

      <footer className="controls-container">
        <div className="palette">
          {COLOR_PALETTE.map((color) => (
            <button
              key={color}
              className={`color-swatch ${myColor === color ? 'selected' : ''}`}
              style={{ backgroundColor: color }}
              onClick={() => setMyColor(color)}
            />
          ))}
        </div>
        <div className="custom-color-picker">
           <label htmlFor="custom-color">커스텀</label>
           <input
             id="custom-color"
             type="color"
             value={myColor}
             onChange={(e) => setMyColor(e.target.value)}
           />
        </div>
      </footer>
    </div>
  );
}

export default App;
