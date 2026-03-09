# HeManaus

Aplicativo Android para apoiar o fluxo de doacao de sangue do Hemoam/AM em Manaus, com autenticacao, agendamento e acompanhamento pos-doacao.

## Visao geral

O app foi construido com Jetpack Compose e organiza a jornada do doador em etapas:

1. Home com contexto de estoque e chamada para agendamento.
2. Autenticacao (email/senha e Google).
3. Cadastro complementar de dados do usuario.
4. Requisitos para doacao.
5. Agendamento (tipo, data e horario).
6. Confirmacao e instrucoes de preparo.
7. Check-in no local.
8. Tela de agradecimento e impacto.

## Funcionalidades atuais

- Navegacao multi-tela com `navigation-compose`.
- UI completa em Jetpack Compose + Material 3.
- Login/cadastro com Firebase Auth (email/senha).
- Integracao preparada para login Google.
- Gerenciamento de estado local de usuario e agendamento via `BookingViewModel`.
- Componentes reutilizaveis para cards, botoes, badges e feedback visual.
- Dados de estoque de sangue e impacto em formato demonstrativo.

## Stack tecnica

- Kotlin 2.0.21
- Android Gradle Plugin 8.13.0
- Jetpack Compose (BOM 2024.09.00)
- Material 3
- AndroidX Navigation Compose 2.9.4
- Firebase Authentication
- Google Identity Services / Credentials API
- Coil (carregamento de imagem)

## Requisitos de ambiente

- Android Studio recente (recomendado: Meerkat ou superior).
- JDK 11.
- Android SDK com:
  - `compileSdk = 36`
  - `minSdk = 26`
  - `targetSdk = 36`
- Emulador Android ou dispositivo fisico.

## Configuracao do projeto

### 1. Firebase

O projeto ja possui `app/google-services.json` versionado. Se voce for usar outro projeto Firebase:

1. Crie/seleciona o app Android no Firebase Console.
2. Baixe o novo `google-services.json`.
3. Substitua em `app/google-services.json`.

### 2. Google Sign-In

A tela de autenticacao usa `default_web_client_id` de recursos gerados pelo plugin do Google Services.

Importante: no estado atual, o login Google esta em modo mock:

- Arquivo: `app/src/main/java/com/example/hemanaus/ui/screens/AuthScreen.kt`
- Variavel: `useMockGoogleLogin = true`

Para usar autenticacao Google real:

1. Ajuste OAuth no Firebase/Google Cloud.
2. Troque `useMockGoogleLogin` para `false`.
3. Garanta SHA-1/SHA-256 corretos no projeto Firebase.

## Como executar

### Android Studio

1. Abra a pasta do projeto.
2. Aguarde o sync do Gradle.
3. Escolha um emulador/dispositivo.
4. Rode a configuracao `app`.

### Linha de comando (Windows)

```powershell
.\gradlew.bat assembleDebug
```

APK gerado em:

`app/build/outputs/apk/debug/`

## Testes

Executar testes unitarios:

```powershell
.\gradlew.bat test
```

Executar testes instrumentados (com dispositivo/emulador conectado):

```powershell
.\gradlew.bat connectedAndroidTest
```

## Estrutura principal

```text
app/src/main/java/com/example/hemanaus/
|- MainActivity.kt
|- core/
|  |- model/
|  |- viewmodel/
|- ui/
   |- components/
   |- screens/
   |- theme/
   |- utils/
```

## Permissoes declaradas

No `AndroidManifest.xml`:

- `INTERNET`
- `ACCESS_NETWORK_STATE`
- `CALL_PHONE`
- `VIBRATE`
- `POST_NOTIFICATIONS`
- `WAKE_LOCK`

## Observacoes

- Ha dados mockados para estoque e algumas metricas de impacto.
- O fluxo de agendamento atual e focado em experiencia e estado local.
- Para producao, recomenda-se persistencia remota (ex.: Firestore) e notificacoes agendadas reais.
