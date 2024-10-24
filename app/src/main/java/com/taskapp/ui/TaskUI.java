package com.taskapp.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.taskapp.exception.AppException;
import com.taskapp.logic.TaskLogic;
import com.taskapp.logic.UserLogic;
import com.taskapp.model.User;

public class TaskUI {
    private final BufferedReader reader;

    private final UserLogic userLogic;

    private final TaskLogic taskLogic;

    private User loginUser;

    public TaskUI() {
        reader = new BufferedReader(new InputStreamReader(System.in));
        userLogic = new UserLogic();
        taskLogic = new TaskLogic();
    }

    /**
     * 自動採点用に必要なコンストラクタのため、皆さんはこのコンストラクタを利用・削除はしないでください
     * 
     * @param reader
     * @param userLogic
     * @param taskLogic
     */
    public TaskUI(BufferedReader reader, UserLogic userLogic, TaskLogic taskLogic) {
        this.reader = reader;
        this.userLogic = userLogic;
        this.taskLogic = taskLogic;
    }

    /**
     * メニューを表示し、ユーザーの入力に基づいてアクションを実行します。
     *
     * @see #inputLogin()
     * @see com.taskapp.logic.TaskLogic#showAll(User)
     * @see #selectSubMenu()
     * @see #inputNewInformation()
     */
    public void displayMenu() {
        System.out.println("タスク管理アプリケーションにようこそ!!");

        // 設問1
        inputLogin();

        // メインメニュー
        boolean flg = true;
        while (flg) {
            try {
                System.out.println("メインメニュー:");
                System.out.println("以下1~3のメニューから好きな選択肢を選んでください。");
                System.out.println("1. タスク一覧, 2. タスク新規登録, 3. ログアウト");
                System.out.print("選択肢：");
                String selectMenu = reader.readLine();



                System.out.println();

                switch (selectMenu) {
                    case "1":
                        System.out.println("タスク一覧を表示します。");
                        taskLogic.showAll(loginUser);
                        selectSubMenu();
                        break;
                    case "2":
                        System.out.println("タスク新規登録を行います。");
                        inputNewInformation();
                        taskLogic.showAll(loginUser);
                        break;
                    case "3":
                        System.out.println("ログアウトしました。");
                        flg = false;
                        break;
                    default:
                        System.out.println("選択肢が誤っています。1~3の中から選択してください。");
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println();
        }
    }

    // private String getStatusString(int status) {
    //     switch (status) {
    //         case 0: return "未着手";
    //         case 1: return "着手中";
    //         case 2: return "完了";
    //         default: return "不明";
    //     }
    // }

    /**
     * ユーザーからのログイン情報を受け取り、ログイン処理を行います。
     *
     * @see com.taskapp.logic.UserLogic#login(String, String)
     */
    // 設問1
    public void inputLogin() {
        boolean flg = true;
        while (flg) {
            try {
                System.out.print("メールアドレスを入力してください：");
                String email = reader.readLine();

                System.out.print("パスワードを入力してください：");
                String password = reader.readLine();

                // ログイン処理を呼び出す
                loginUser = userLogic.login(email, password);
                System.out.println();
                flg = false;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (AppException e) {
                System.out.println(e.getMessage());
            }
            System.out.println();
        }
    }

    /**
     * ユーザーからの新規タスク情報を受け取り、新規タスクを登録します。
     *
     * @see #isNumeric(String)
     * @see com.taskapp.logic.TaskLogic#save(int, String, int, User)
     */
    public void inputNewInformation() {
        boolean flg = true;
        int code = 0;
        String name = "";
        int repUserCode = 0;

        while (flg) {
            try {
                System.out.print("タスクコードを入力してください：");
                String codeInput = reader.readLine();
                if (!isNumeric(codeInput)) {
                    System.out.println("コードは半角の数字で入力してください");
                    continue;
                }
                code = Integer.parseInt(codeInput);

                System.out.print("タスク名を入力してください：");
                name = reader.readLine();
                if (name.length() > 10) {
                    System.out.println("タスク名は10文字以内で入力してください");
                    continue;
                }

                System.out.print("担当するユーザーのコードを選択してください：");
                String repUserCodeInput = reader.readLine();
                if (!isNumeric(repUserCodeInput)) {
                    System.out.println("ユーザーのコードは半角の数字で入力してください");
                    continue;
                }
                repUserCode = Integer.parseInt(repUserCodeInput);

                // タスクを保存
                taskLogic.save(code, name, repUserCode, loginUser);
                System.out.println("タスクが新規登録されました。");
                flg = false; // 入力が成功したのでループを抜ける
            } catch (AppException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isNumeric(String inputText) {
        return inputText.matches("\\d+");//TODO
    }

    // private boolean isUserCodeValid(int userCode) {
    //     // Validate user code against users.csv or appropriate source
    //     // Dummy implementation; replace with actual validation logic
    //     return true; // Example: Always returns true for demo
    // }

    /**
     * タスクのステータス変更または削除を選択するサブメニューを表示します。
     *
     * @see #inputChangeInformation()
     * @see #inputDeleteInformation()
     */
    public void selectSubMenu() {
        // サブメニューの表示
        boolean flg = true;
        while (flg) {
            try {
                System.out.println("以下1~2から好きな選択肢を選んでください。"); // ⑨
                System.out.println("1. タスクのステータス変更, 2. メインメニューに戻る"); // ⑨
                System.out.print("選択肢：");
                String choice = reader.readLine();

                switch (choice) {
                    case "1":
                        inputChangeInformation(); // タスクのステータス変更処理を呼び出す
                        break;
                    case "2":
                        flg = false; // メインメニューに戻る
                        break;
                    default:
                        System.out.println("選択肢が誤っています。1~2の中から選択してください。");
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * ユーザーからのタスクステータス変更情報を受け取り、タスクのステータスを変更します。
     *
     * @see #isNumeric(String)
     * @see com.taskapp.logic.TaskLogic#changeStatus(int, int, User)
     */
    public void inputChangeInformation() {//TODO
        boolean flg = true;
        while (flg) {
            try {
                System.out.println("ステータスを変更するタスクコードを入力してください");
                System.out.print("選択肢： ");
                String codeInput = reader.readLine();

                int code;
            try {
                code = Integer.parseInt(codeInput);
            } catch (NumberFormatException e) {
                System.out.println("コードは半角の数字で入力してください");
                continue;
            }

            System.out.println("新しいステータスを入力してください：");
            System.out.println("1. 着手中, 2. 完了");
            System.out.print("選択肢:");
            String statusInput = reader.readLine();
            int newStatus;
            try {
                newStatus = Integer.parseInt(statusInput);
            } catch (NumberFormatException e) {
                System.out.println("ステータスは半角の数字で入力してください");
                continue;
            }

            // タスクのステータスを変更
            try {
                taskLogic.changeStatus(code, newStatus, loginUser);
                System.out.println("タスクのステータスが変更されました。");
                flg = false; // 入力が成功したのでループを抜ける
            } catch (AppException e) {
                System.out.println(e.getMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
            }
        }
    }
    /**
     * ユーザーからのタスク削除情報を受け取り、タスクを削除します。
     *
     * @see #isNumeric(String)
     * @see com.taskapp.logic.TaskLogic#delete(int)
     */
    // public void inputDeleteInformation() {
    // }

    /**
     * 指定された文字列が数値であるかどうかを判定します。
     * 負の数は判定対象外とする。
     *
     * @param inputText 判定する文字列
     * @return 数値であればtrue、そうでなければfalse
     */
    // public boolean isNumeric(String inputText) {
    // return false;
    // }
}
