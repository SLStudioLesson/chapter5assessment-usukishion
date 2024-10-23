# Chapter5アセスメント

## 試験概要

試験時間：180分

CSVを使ったタスク管理アプリケーションを実装します。
本試験では必要なクラスとメソッドの定義は既に済ませてコメントアウトしています。
また以下のようにメインメニューを表示する処理は実装しています。
```
以下1~3のメニューから好きな選択肢を選んでください。
1. タスク一覧, 2. タスク新規登録, 3. ログアウト
```
皆さんはこのメインメニューが表示される前のログイン機能と、メインメニューの選択肢によって動作するCRUD機能を実装していきアプリケーションの完成を目指します。

設問の内容と以下条件に沿って機能を追加してください。

- 出力例とJavadocをもとに必要な処理を実装すること
- 数値の判定は`isNumeric`メソッドを実装し利用すること
- 実装前に処理フローをコメントアウトで記述すること
- 実装完了後、自己採点（動作確認）を行うこと
- 自己採点完了後、次の設問に進むこと
- 全てのプログラムが実行可能であること
- コンパイルエラー状態での提出は禁止
- メソッド名や引数・返り値の型などこちらで既に用意しているものを勝手に変更した場合は不正解となる
  - 独自でメソッドやフィールドを追加するのも原則禁止とします
- 出力内容が指示通りでない場合（半角スペースの有無も含めて）不正解となるので、出力内容はコピペで記述すること
- 実行時に`Build failed, do you want to continue.`という警告が出たときは`continue`を選択すると実行可能
- IOExceptionは、throwsせずにそのメソッド内でcatchして `printStackTRace`メソッドを実行してください
  - こちらで既にtry-catchを書いているメソッドもありますが、設問の都合上書いてないメソッドもあります。
- `AppException`は、`exception`パッケージに用意しているのでそれを利用すること

### CSVデータの仕様
必要なcsvファイルは`src/main/resources`フォルダ内に保存されています。

**users.csv**

以下ユーザーデータを保存するファイルです。

- Code：ユーザーのコード（整数）
- Name：ユーザー名（文字列）
- Email：メールアドレス（文字列）
- Password：パスワード（文字列）

```
Code,Name,Email,Password
1,鈴木一郎,test1@example.com,password1
2,鈴木二郎,test2@example.com,password2
```

**tasks.csv**

以下タスクデータを保存するファイルです

- Code：タスクのコード（整数）
- Name：タスク名（文字列）
- Status：タスクのステータス（整数）
  - 0→未着手、1→着手中、2→完了
- Rep_User_Code：タスクを担当するユーザーコード（整数）

```
Code,Name,Status,Rep_User_Code
1,taskA,0,1
2,taskB,0,2
3,taskC,2,2
4,taskD,1,2
```

**logs.csv**

タスクのステータスが変更されたときのログデータを保存するファイルです

- Task_Code：ステータスが変更されたタスクのコード（整数）
- Change_User_Code：タスクのステータスを変更したユーザーコード（整数）
- Status：変更したステータス（整数）
  - 0→未着手、1→着手中、2→完了
- Change_Date：タスクのステータスを変更した日付（日付）

```
Task_Code,Change_User_Code,Status,Change_Date
1,1,0,2024-01-10
2,1,0,2024-01-10
3,1,0,2024-01-10
4,1,0,2024-01-10
3,2,1,2024-01-11
3,2,2,2024-01-12
4,2,1,2024-01-13
```

## 設問1

### 目安時間

30分

### 設問

以下条件と出力例を基にログイン機能を実装してください。

**実装・修正が必要なメソッド**
- TaskUI#displayMenu
- TaskUI#inputLogin
- UserLogic#login
- UserDataAccess#findByEmailAndPassword

**仕様**
- メールアドレスとパスワードを入力させること
  - メールアドレスを入力させるときは「メールアドレスを入力してください：」というメッセージを表示する
  - パスワードを入力させるときは「パスワードを入力してください：」というメッセージを表示する
- 入力された値に合致するデータを `users.csv`の中から探すこと
  - 合致するデータが見つかった場合は、メニューの一覧が表示されること
  - 合致するデータが見つからなかった場合は、AppExceptionをスローすること
    - スローするときのメッセージは「既に登録されているメールアドレス、パスワードを入力してください」とする
    - AppExceptionがスローされたらTaskUI側でメッセージを表示し、再度メールアドレスの入力に戻る


**完成した場合の出力例**

```
タスク管理アプリケーションにようこそ!!
メールアドレスを入力してください：hogefuga@example.com
パスワードを入力してください：password1
既に登録されているメールアドレス、パスワードを入力してください

メールアドレスを入力してください：test1@example.com
パスワードを入力してください：hogefuga
既に登録されているメールアドレス、パスワードを入力してください

メールアドレスを入力してください：test1@example.com
パスワードを入力してください：password1
ユーザー名：鈴木一郎でログインしました。

以下1~3のメニューから好きな選択肢を選んでください。
1. タスク一覧, 2. タスク新規登録, 3. ログアウト
```

### プログラムのテスト（動作確認）

**テストケース1**
1. アプリケーションを起動する
2. 「タスク管理アプリケーションへようこそ!!」というメッセージの後に、「メールアドレスを入力してください：」というメッセージが出力されることを確認する
3. メールアドレスの標準入力を求められることを確認する
4. メールアドレスを入力後に「パスワードを入力してください：」というメッセージが出力されることを確認する
5. パスワードの標準入力を求められることを確認する

**テストケース2**
1. メールアドレス「test1@example.com」、パスワード「password1」と入力する
2. 以下のメインメニューが表示されることを確認する
  ```
  以下1~3のメニューから好きな選択肢を選んでください。
  1. タスク一覧, 2. タスク新規登録, 3. ログアウト
  ```

**テストケース3**
1. メールアドレス「hogefuga@example.com」、パスワード「hogefuga」と入力する
2. 「既に登録されているメールアドレス、パスワードを入力してください」というメッセージが表示されることを確認する
3. 再度メールアドレス・パスワードの入力に戻ることを確認する


## 設問2

### 目安時間

30分

### 設問

以下条件を基に `tasks.csv` のデータを一覧表示する機能を実装してください。

**実装・修正が必要なメソッド**
- TaskUI#displayMenu
- TaskLogic#showAll
- TaskDataAccess#findAll
- UserDataAccess#findByCode

**仕様**
- ログイン後に表示されるメインメニューから選択できるようにすること
- `status` の値に応じて表示を変えるようにすること
  - 0→未着手、1→着手中、2→完了
- タスクを担当するユーザーの名前が表示できるようにすること
  - 担当者が今ログインしてるユーザーなら、「あなたが担当しています」と表示する
    - そうでないなら、担当してるユーザー名を表示する

**完成した場合の出力例（鈴木一郎でログインした場合）**

```
以下1~3のメニューから好きな選択肢を選んでください。
1. タスク一覧, 2. タスク新規登録, 3. ログアウト
選択肢：1

1. タスク名：taskA, 担当者名：あなたが担当しています, ステータス：未着手
2. タスク名：taskB, 担当者名：鈴木二郎が担当しています, ステータス：未着手
3. タスク名：taskC, 担当者名：鈴木二郎が担当しています, ステータス：完了
4. タスク名：taskD, 担当者名：鈴木二郎が担当しています, ステータス：着手中
```

### プログラムのテスト（動作確認）

**テストケース1**
1. 「鈴木一郎」ユーザーでログインする
2. メインメニューにて「1」を選択する
3. 以下の通りに`tasks.csv`のデータが全て表示されることを確認する
  ```
  1. タスク名：taskA, 担当者名：あなたが担当しています, ステータス：未着手
  2. タスク名：taskB, 担当者名：鈴木二郎が担当しています, ステータス：未着手
  3. タスク名：taskC, 担当者名：鈴木二郎が担当しています, ステータス：完了
  4. タスク名：taskD, 担当者名：鈴木二郎が担当しています, ステータス：着手中
  ```

**テストケース2**
1. 「鈴木二郎」ユーザーでログインする
2. メインメニューにて「1」を選択する
3. 以下の通りに`tasks.csv`のデータが全て表示されることを確認する
  ```
  1. タスク名：taskA, 担当者名：鈴木一郎が担当しています, ステータス：未着手
  2. タスク名：taskB, 担当者名：あなたが担当しています, ステータス：未着手
  3. タスク名：taskC, 担当者名：あなたが担当しています, ステータス：完了
  4. タスク名：taskD, 担当者名：あなたが担当しています, ステータス：着手中
  ```

## 設問3

### 目安時間

40分

### 設問

以下条件を基に、タスク新規登録機能を実装してください。

**実装・修正が必要なメソッド**
- TaskUI#displayMenu
- TaskUI#inputNewInformation
- TaskUI#isNumeric
- TaskLogic#save
- TaskDataAccess#save
- LogDataAccess#save

**仕様**
- ログイン後に表示されるメインメニューから選択できるようにすること
- タスクコード、タスク名、タスクを担当するユーザーコードを入力させる
  - タスクコードを入力させるときは「タスクコードを入力してください：」と表示する
  - タスク名を入力させるときは「タスク名を入力してください：」と表示する
  - 担当するユーザーコードを入力させるときは「担当するユーザーのコードを選択してください：」と表示する
- 入力値に対して以下の仕様でバリデーションを行うこと
  - タスクコードは数字か
    - 仕様を満たさない場合、「コードは半角の数字で入力してください」と表示し再度タスクコードの入力に戻る
  - タスク名は10文字以内か
    - 仕様を満たさない場合、「タスク名は10文字以内で入力してください」と表示し再度タスクコードの入力に戻る
  - 担当するユーザーコードは数字か
    - 仕様を満たさない場合、「ユーザーのコードは半角の数字で入力してください」と表示し再度タスクコードの入力に戻る
- 担当するユーザーコードが `users.csv`に登録されていない場合、AppExceptionをスローする
  - スローするときのメッセージは「存在するユーザーコードを入力してください」とする
  - AppExceptionがスローされたらTaskUI側でメッセージを表示し、再度タスクコードの入力に戻る
- `tasks.csv`にデータを1件作成する
  - `Status`は `0`
- `logs.csv`にデータを1件作成する
  - `Status`は `0`
  - `Change_User_Code`は今ログインしてるユーザーコード
  - `Change_Date`は今日の日付

**完成した場合の出力例（鈴木一郎でログインした場合）**

```
以下1~3のメニューから好きな選択肢を選んでください。
1. タスク一覧, 2. タスク新規登録, 3. ログアウト
選択肢：2

タスクコードを入力してください：test
コードは半角の数字で入力してください

タスクコードを入力してください：5
タスク名を入力してください：taskEtaskEt
タスク名は10文字以内で入力してください

タスクコードを入力してください：5
タスク名を入力してください：taskE
担当するユーザーのコードを選択してください：test
ユーザーのコードは半角の数字で入力してください

タスクコードを入力してください：5
タスク名を入力してください：taskE
担当するユーザーのコードを選択してください：50000
存在するユーザーコードを入力してください

タスクコードを入力してください：5
タスク名を入力してください：taskE
担当するユーザーのコードを選択してください：2
taskEの登録が完了しました。
```

### プログラムのテスト（動作確認）

**事前手順**
1. 「鈴木一郎」ユーザーでログインする
2. メインメニューにて「2」を選択する

**テストケース1**
1. 「タスクコードを入力してください：」と表示されることを確認する
2. タスクコードの標準入力を求められることを確認する
3. タスクコードの入力後に「タスク名を入力してください：」と表示されることを確認する
4. タスク名の標準入力を求められることを確認する
5. タスク名の入力後に「担当するユーザーのコードを選択してください：」と表示されることを確認する
6. 担当するユーザーコードの標準入力を求められることを確認する

**テストケース2**
1. タスクコードに「test」と入力する
2. 「コードは半角の数字で入力してください」と表示されることを確認する
3. タスクコードの入力に戻ることを確認する

**テストケース3**
1. タスクコードに「5」と入力する
2. タスク名に「taskEtaskEt」と入力する
3. 「タスク名は10文字以内で入力してください」と表示されることを確認する
4. タスクコードの入力に戻ることを確認する

**テストケース4**
1. タスクコードに「5」と入力する
2. タスク名に「taskE」と入力する
3. 担当するユーザーコードに「test」と入力する
4. 「ユーザーのコードは半角の数字で入力してください」と表示されることを確認する
5. タスクコードの入力に戻ることを確認する

**テストケース5**
1. タスクコードに「5」と入力する
2. タスク名に「taskE」と入力する
3. 担当するユーザーコードに「50000」と入力する
4. 「存在するユーザーコードを入力してください」と表示されることを確認する
5. タスクコードの入力に戻ることを確認する

**テストケース6**
1. タスクコードに「5」と入力する
2. タスク名に「taskE」と入力する
3. 担当するユーザーコードに「2」と入力する
4. `tasks.csv`に以下データが1行登録されていることを確認する
  ```
  5,taskE,0,2
  ```
1. `logs.csv`に以下データが1行登録されていることを確認する（Change_Dateはあくまで例なので、今日の日付が入っていることを確認する）
  ```
  5,1,0,2024-01-13
  ```


## 設問4

### 目安時間

40分

### 設問

以下条件を基にタスクのステータスを更新する機能を実装してください。

**実装・修正が必要なメソッド**
- TaskUI#displayMenu
- TaskUI#selectSubMenu
- TaskUI#inputChangeInformation
- TaskLogic#changeStatus
- TaskDataAccess#findByCode
- TaskDataAccess#update

**仕様**
- タスク一覧表示後に、ステータス更新機能を選択できるサブメニューを追加すること
  ```
  以下1~2から好きな選択肢を選んでください。
  1. タスクのステータス変更, 2. メインメニューに戻る
  ```

- サブメニューにて1を選択した場合、以下ステータス更新機能を実行し、2を選択した場合メインメニューの選択に戻ること
- ステータスを変更するタスクコード、変更後のステータスを入力させること
  - タスクコードを入力させるときは「ステータスを変更するタスクコードを入力してください：」と表示する
  - ステータスを入力させるときは以下のように表示する
    ```
    どのステータスに変更するか選択してください。
    1. 着手中, 2. 完了
    選択肢：
    ```
- 入力値に対して以下の仕様でバリデーションを行うこと
  - タスクコードは数字か
    - 仕様を満たさない場合、「コードは半角の数字で入力してください」と表示し再度タスクコードの入力に戻る
  - 変更後のステータスは数字か
    - 仕様を満たさない場合、「ステータスは半角の数字で入力してください」と表示し再度タスクコードの入力に戻る
  - 変更後のステータスは、1または2か
    - 仕様を満たさない場合、「ステータスは1・2の中から選択してください」と表示し再度タスクコードの入力に戻る
- 以下仕様に沿わない場合、AppExceptionをスローする
  - 入力されたタスクコードが `tasks.csv`に存在しない場合
    - スローするときのメッセージは「存在するタスクコードを入力してください」とする
  - `tasks.csv`に存在するタスクのステータスが、変更後のステータスの1つ前じゃない場合
    ```
    変更可能な例：「未着手」から「着手中」、または「着手中」から「完了」
    変更できない例：「未着手」から「完了」、「着手中」から「着手中」、「完了」から他のステータス
    ```
    - スローするときのメッセージは「ステータスは、前のステータスより1つ先のもののみを選択してください」とする
  - AppExceptionがスローされたらTaskUI側でメッセージを表示し、再度タスクコードの入力に戻る
- `tasks.csv`の該当タスクのステータスを変更後のステータスに更新する
- `logs.csv`にデータを1件作成する
  - `Status`は変更後のステータス
  - `Change_User_Code`は今ログインしてるユーザーコード
  - `Change_Date`は今日の日付

**出力例（鈴木一郎でログインした場合）**

```
1. タスク名：taskA, 担当者名：あなたが担当しています, ステータス：未着手
2. タスク名：taskB, 担当者名：鈴木二郎が担当しています, ステータス：未着手
3. タスク名：taskC, 担当者名：鈴木二郎が担当しています, ステータス：完了
4. タスク名：taskD, 担当者名：鈴木二郎が担当しています, ステータス：着手中
5. タスク名：taskE, 担当者名：鈴木二郎が担当しています, ステータス：未着手
以下1~2から好きな選択肢を選んでください。
1. タスクのステータス変更, 2. メインメニューに戻る
選択肢：1

ステータスを変更するタスクコードを入力してください：test
コードは半角の数字で入力してください

ステータスを変更するタスクコードを入力してください：5
どのステータスに変更するか選択してください。
1. 着手中, 2. 完了
選択肢：test
ステータスは半角の数字で入力してください

ステータスを変更するタスクコードを入力してください：5
どのステータスに変更するか選択してください。
1. 着手中, 2. 完了
選択肢：3
ステータスは1・2の中から選択してください

ステータスを変更するタスクコードを入力してください：10000
どのステータスに変更するか選択してください。
1. 着手中, 2. 完了
選択肢：2
存在するタスクコードを入力してください

ステータスを変更するタスクコードを入力してください：5
どのステータスに変更するか選択してください。
1. 着手中, 2. 完了
選択肢：2
ステータスは、前のステータスより1つ先のもののみを選択してください

ステータスを変更するタスクコードを入力してください：5
どのステータスに変更するか選択してください。
1. 着手中, 2. 完了
選択肢：1
ステータスの変更が完了しました。
```

### プログラムのテスト（動作確認）

**事前手順**
1. 「鈴木一郎」ユーザーでログインする
2. メインメニューにて「1」を選択する
3. サブメニューにて「1」を選択する

**テストケース1**
1. 「ステータスを変更するタスクコードを入力してください：」と表示されることを確認する
2. タスクコードの標準入力を求められることを確認する
3. タスクコードの入力後に以下の文言が表示されることを確認する
  ```
  どのステータスに変更するか選択してください。
  1. 着手中, 2. 完了
  選択肢：
  ```
4. ステータスの標準入力を求められることを確認する

**テストケース2**
1. タスクコードに「test」と入力する
2. 「コードは半角の数字で入力してください」と表示されることを確認する
3. タスクコードの入力に戻ることを確認する

**テストケース3**
1. タスクコードに「5」と入力する
2. ステータスに「test」と入力する
3. 「ステータスは半角の数字で入力してください」と表示されることを確認する
4. タスクコードの入力に戻ることを確認する

**テストケース4**
1. タスクコードに「5」と入力する
2. ステータスに「3」と入力する
3. 「ステータスは1・2の中から選択してください」と表示されることを確認する
4. タスクコードの入力に戻ることを確認する

**テストケース5**
1. タスクコードに「10000」と入力する
2. ステータスに「2」と入力する
3. 「存在するタスクコードを入力してください」と表示されることを確認する
4. タスクコードの入力に戻ることを確認する

**テストケース6**
1. タスクコードに「5」と入力する
2. ステータスに「2」と入力する
3. 「ステータスは、前のステータスより1つ先のもののみを選択してください」と表示されることを確認する
4. タスクコードの入力に戻ることを確認する

**テストケース7**
1. タスクコードに「5」と入力する
2. ステータスに「1」と入力する
3. `tasks.csv`の該当タスクのステータスが「1」に更新されていることを確認する
4. `logs.csv`に以下データが1行登録されていることを確認する（Change_Dateはあくまで例なので、今日の日付が入っていることを確認する）
  ```
  5,1,1,2024-01-13
  ```



## 設問5

### 目安時間

40分

### 設問

以下条件を基にタスクを削除する機能を実装してください。

**実装・修正が必要なメソッド**
- TaskUI#selectSubMenu
- TaskUI#inputDeleteInformation
- TaskLogic#delete
- TaskDataAccess#delete
- LogDataAccess#findAll
- LogDataAccess#deleteByTaskCode

**仕様**
- 設問4にて実装したサブメニューに、削除用の選択肢を追加すること
  ```
  以下1~3から好きな選択肢を選んでください。
  1. タスクのステータス変更, 2. タスク削除, 3. メインメニューに戻る
  ```

- サブメニューにて2を選択した場合、以下タスク削除機能を実行すること
- 削除するタスクコード入力させること
  - タスクコードを入力させるときは「削除するタスクコードを入力してください：」と表示する
- 入力値に対して以下の仕様でバリデーションを行うこと
  - タスクコードは数字か
    - 仕様を満たさない場合、「コードは半角の数字で入力してください」と表示し再度タスクコードの入力に戻る
- 以下仕様に沿わない場合、AppExceptionをスローする
  - 入力されたタスクコードが `tasks.csv`に存在しない場合
    - スローするときのメッセージは「存在するタスクコードを入力してください」とする
  - 該当タスクのステータスが、完了（2）ではない場合
    - スローするときのメッセージは「ステータスが完了のタスクを選択してください」とする
  - AppExceptionがスローされたらTaskUI側でメッセージを表示し、再度タスクコードの入力に戻る
- `tasks.csv`の該当タスクデータを削除すること
- `logs.csv`の該当タスクコードがあるデータを全て削除すること

**出力例（鈴木一郎でログインした場合）**

```
1. タスク名：taskA, 担当者名：あなたが担当しています, ステータス：未着手
2. タスク名：taskB, 担当者名：鈴木二郎が担当しています, ステータス：未着手
3. タスク名：taskC, 担当者名：鈴木二郎が担当しています, ステータス：完了
4. タスク名：taskD, 担当者名：鈴木二郎が担当しています, ステータス：着手中
5. タスク名：taskE, 担当者名：鈴木二郎が担当しています, ステータス：未着手
以下1~3から好きな選択肢を選んでください。
1. タスクのステータス変更, 2. タスク削除, 3. メインメニューに戻る
選択肢：2

削除するタスクコードを入力してください：test
コードは半角の数字で入力してください

削除するタスクコードを入力してください：10000
存在するタスクコードを入力してください

削除するタスクコードを入力してください：5
ステータスが完了のタスクを選択してください

削除するタスクコードを入力してください：3
taskCの削除が完了しました。
```

### プログラムのテスト（動作確認）

**事前手順**
1. 「鈴木一郎」ユーザーでログインする
2. メインメニューにて「1」を選択する
3. サブメニューにて「2」を選択する

**テストケース1**
1. 「削除するタスクコードを入力してください：」と表示されることを確認する
2. タスクコードの標準入力を求められることを確認する

**テストケース2**
1. タスクコードに「test」と入力する
2. 「コードは半角の数字で入力してください」と表示されることを確認する
3. タスクコードの入力に戻ることを確認する

**テストケース3**
1. タスクコードに「10000」と入力する
2. 「存在するタスクコードを入力してください」と表示されることを確認する
3. タスクコードの入力に戻ることを確認する

**テストケース4**
1. タスクコードに「5」と入力する
2. 「ステータスが完了のタスクを選択してください」と表示されることを確認する
3. タスクコードの入力に戻ることを確認する

**テストケース5**
1. タスクコードに「3」と入力する
2. `tasks.csv`のCode「3」のデータが削除されていることを確認する
3. `logs.csv`のTask_Code「3」のデータが全て削除されていることを確認する