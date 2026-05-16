# NE-O-RDINARY THON — Team O (Android)

Compose + MVVM + Retrofit 기반 안드로이드 앱.

## 폴더 구조

```
app/src/main/java/com/example/hackerton/
├── MainActivity.kt
├── data/
│   ├── local/         # Room / DataStore
│   ├── model/         # DTO, 응답/요청 데이터 클래스
│   ├── network/       # Retrofit ApiService, NetworkClient
│   └── repository/    # Repository 구현
├── ui/
│   ├── components/    # 공통 Composable (BaseScreen 등)
│   ├── navigation/    # AppNavHost, Routes
│   ├── screens/       # 화면 단위 (feature 폴더로 추가)
│   └── theme/         # Color, Theme, Typography
└── util/              # 확장 함수, 공통 유틸
```

## 작업 규칙

- 화면 추가: `ui/screens/<feature>/` 아래 `XxxScreen.kt`, `XxxViewModel.kt`
- API 추가: `data/network/ApiService.kt` 에 엔드포인트, `data/model/` 에 DTO
- 브랜치: 본인 닉네임 브랜치에서 작업 후 PR
