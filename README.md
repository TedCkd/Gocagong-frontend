12-01
1. 인증
AuthInterceptor: JWT 토큰 자동 헤더 추가
RetrofitClient: 인증 필요/불필요 API 클라이언트 제공

2. 리뷰
내 리뷰 조회: GET /api/review/me
리뷰 삭제: DELETE /api/review/{reviewId}
Adapter: MyReviewsAdapter (이미지 표시, 삭제 버튼)

3. 북마크 (저장한 카페)
목록 조회: GET /api/bookmarks/me
Adapter: BookmarkAdapter

4. 카페 정보
리스트 조회: GET /api/cafes
상세 조회: GET /api/cafes/{cafeId}

5. 사용자 정보
내 정보 조회: GET /api/users/me
