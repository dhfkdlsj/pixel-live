## 🚀 작업 내용 (What is this PR for?)

---
### 📌 관련 이슈
- closes #

---
### ✨ 핵심 변경 사항 (Core Changes)
- (예: domain 모듈에 Pixel 엔티티와 PixelId 임베디드 키 추가)
- (예: RedisConfig 설정 및 쿨타임 검사 Service 로직 추가)
-

---
### 💡 특이 사항 (Detailed Notes)
- 동시성 문제 해결을 위해 JPQL Direct Update 방식을 채택했습니다. (참고: #3)
- 쿨타임 Key는 사용자 IP를 기반으로 합니다.
-

---
### ✅ 테스트 및 검증 (Tests and Verification Checklist)
- [ ] 단위/통합 테스트를 통과했습니다.
- [ ] Docker 환경에서 DB와 Redis가 정상 작동함을 확인했습니다.
- [ ] (수동 테스트) 픽셀 업데이트 기능과 쿨타임(10초)이 정상 동작함을 확인했습니다.

---
### ⚠️ 리뷰어 참고 사항 (Reviewer Checklist)
- [ ] DB 접근 시 쿼리 N+1 문제가 발생하지 않는지? (Show SQL 확인)
- [ ] JPA 연관 관계 설정이 올바른지?
- [ ] 성능 저하를 유발하는 로직은 없는지?
- [ ] 코드 컨벤션을 준수했는지?
