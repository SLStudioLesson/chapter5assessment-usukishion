package com.taskapp.dataaccess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.taskapp.model.User;

public class UserDataAccess {
    private final String filePath;

    public UserDataAccess() {
        filePath = "app/src/main/resources/users.csv";
    }

    /**
     * 自動採点用に必要なコンストラクタのため、皆さんはこのコンストラクタを利用・削除はしないでください
     * @param filePath
     */
    public UserDataAccess(String filePath) {
        this.filePath = filePath;
    }

    /**
     * メールアドレスとパスワードを基にユーザーデータを探します。
     * @param email メールアドレス
     * @param password パスワード
     * @return 見つかったユーザー
     */
    public User findByEmailAndPassword(String email, String password) {
        User user = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");

                // メールアドレス、パスワードが一致しなかったらスキップする
                if (!(values[2].equals(email) && values[3].equals(password))) continue;

                // メールアドレス、パスワードが一致したらそのデータをUserオブジェクトにマッピング
                int code = Integer.parseInt(values[0]);
                String name = values[1];
                String userEmail = values[2];
                String userPassword = values[3];
                user = new User(code, name, userEmail, userPassword);
                return user;
            }
        } catch (IOException e) {
            e.printStackTrace();;
        }
        return null;
    }

    /**
     * コードを基にユーザーデータを取得します。
     * @param code 取得するユーザーのコード
     * @return 見つかったユーザー
     */
    //設問2
    public User findByCode(int code) {
        User user = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); // ヘッダー行をスキップ
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");

                int userCode = Integer.parseInt(values[0]);

                if (code != userCode) {
                    continue;
                }

                String name = values[1];
                String email = values[2];
                String password = values[3];

                user = new User(userCode, name, email, password);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }
}