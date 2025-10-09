# はじめに
- 本リポジトリはjava学習者Msk-lagがRaiseTechで学習を進めていく中で作成したものになります
- ご利用に際して発生するトラブルについては責任を負いかねます
# アプリ名
受講生管理アプリ
# アプリ概要
- 「生徒情報」「コース情報」「申し込み情報」をそれぞれ管理します
- 上記情報を「新規登録」「更新」「検索」することができます
# デモ動画
- https://youtu.be/__7SCi1HuI8
# 仕様技術
## バックエンド  
![Java](https://img.shields.io/badge/Java-21-%23ED8B00?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.5.0-%236DB33F?logo=spring)
## インフラ DB  
![AWS EC2](https://img.shields.io/badge/AWS-EC2-%23FF9900?logo=amazon-aws)
![AWS RDS](https://img.shields.io/badge/AWS-RDS-%23232F3E?logo=amazon-aws)
![MySQL](https://img.shields.io/badge/MySQL-%234479A1?logo=mysql&logoColor=white)
## 使用ツール  
![MyBatis](https://img.shields.io/badge/MyBatis-%23C73A36?logo=mybatis&logoColor=white)
![JUnit](https://img.shields.io/badge/JUnit-%2325A162?logo=junit5&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-%23FF6C37?logo=postman&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-%2385EA2D?logo=swagger&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-%23181717?logo=github&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-%232088FF?logo=githubactions&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ%20IDEA-%23000000?logo=intellijidea&logoColor=white)
# 利用方法
本アプリはバックエンドAPIのみを実装しており、フロントエンドは未作成です  
そのため、APIの動作確認には **Postman**  を使用してください  
普段はリソース管理のためサーバーを閉じています  
実際に動作確認したい方は **おわりに** へ記載しているメールアドレスまでご連絡ください
## API一覧
### 1. 受講生新規登録
- **URL:** `http://StudentManagementALB-1815001307.ap-northeast-1.elb.amazonaws.com/registerStudent`
- **Method:** POST
- **説明:** JSON形式で新規受講生を登録します。

---

### 2. 受講生更新
- **URL:** `http://StudentManagementALB-1815001307.ap-northeast-1.elb.amazonaws.com/updateStudent`
- **Method:** PUT
- **説明:** JSON形式で受講生情報を更新します。

---

### 3. 受講生一覧検索
- **URL:** `http://StudentManagementALB-1815001307.ap-northeast-1.elb.amazonaws.com/studentList`
- **Method:** GET
- **説明:** 全受講生情報と紐づくコース・申し込み情報を取得します。

---

### 4. 受講生単一検索
- **URL:** `http://StudentManagementALB-1815001307.ap-northeast-1.elb.amazonaws.com/student/{studentId}`
- **Method:** GET
- **説明:** 指定した受講生IDに紐づく生徒情報とコース・申し込み情報を取得します。

---

### 5. 絞り込み検索
- **URL:** `http://StudentManagementALB-1815001307.ap-northeast-1.elb.amazonaws.com/students?fullName=&age=&gender=`
- **Method:** GET
- **説明:** 名前・年齢・性別のいずれか、または組み合わせで受講生情報を検索します。  
  - 「名前」「年齢」「性別」のいずれかを必ず入力してください。  

---

**補足:**  
- 全操作はデモ動画内で操作方法を確認できます。  
- JSON形式のリクエストについては動画をご参照ください。

# 実装予定の機能
- フロントエンドの実装  
- 「名前」「年齢」「性別」以外の要素でも検索できるよう絞り込み検索の拡張
- コース情報の絞り込み検索の実装  
# おわりに
- 本リポジトリは、学習の一区切りとして公開させていただきました。
- 感想やコメント等あれば**zapper2234@gmail.com**までご連絡いただけますと幸いです
