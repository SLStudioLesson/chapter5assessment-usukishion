package com.taskapp.dataaccess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.taskapp.exception.AppException;
import com.taskapp.model.Task;
import com.taskapp.model.User;

public class TaskDataAccess {

    private final String filePath;

    private final UserDataAccess userDataAccess;

    public TaskDataAccess() {
        filePath = "app/src/main/resources/tasks.csv";
        userDataAccess = new UserDataAccess();
    }

    /**
     * 自動採点用に必要なコンストラクタのため、皆さんはこのコンストラクタを利用・削除はしないでください
     * 
     * @param filePath
     * @param userDataAccess
     */
    public TaskDataAccess(String filePath, UserDataAccess userDataAccess) {
        this.filePath = filePath;
        this.userDataAccess = userDataAccess;
    }

    /**
     * CSVから全てのタスクデータを取得します。
     *
     * @see com.taskapp.dataaccess.UserDataAccess#findByCode(int)
     * @return タスクのリスト
     */
    // 設問2
    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); // ヘッダー行をスキップ
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length != 4) {
                    continue; // CSVフォーマットに誤りがある場合スキップ
                }

                int code = Integer.parseInt(values[0]);
                String name = values[1];
                int status = Integer.parseInt(values[2]);
                int repUserCode = Integer.parseInt(values[3]);
                User repUser = userDataAccess.findByCode(repUserCode);

                // 担当ユーザーが存在しない場合の処理を追加
                if (repUser == null) {
                    System.out.println("担当者コード " + repUserCode + " に該当するユーザーが見つかりません。");
                    continue;
                }

                Task task = new Task(code, name, status, repUser);
                tasks.add(task);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    /**
     * タスクをCSVに保存します。
     *
     * @param task 保存するタスク
     */
    public void save(Task task) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            String line = createLine(task);
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String createLine(Task task) {
        return task.getCode() + "," + task.getName() + "," + task.getStatus() + "," + task.getRepUser().getCode();
    }

    /**
     * コードを基にタスクデータを1件取得します。
     * 
     * @param code 取得するタスクのコード
     * @return 取得したタスク
     */
    public Task findByCode(int code) throws AppException { //TODO
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); // ヘッダーをスキップ
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (Integer.parseInt(values[0]) == code) {
                    int status = Integer.parseInt(values[2]);
                    int repUserCode = Integer.parseInt(values[3]);
                    User repUser = userDataAccess.findByCode(repUserCode);
                    if (repUser == null) {
                        throw new AppException("担当者コードに該当するユーザーが見つかりません。");
                    }
                    return new Task(code, values[1], status, repUser);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new AppException("存在するタスクコードを入力してください");
    }

    /**
     * タスクデータを更新します。
     * 
     * @param updateTask 更新するタスク
     */
    public void update(Task updateTask) { //TODO
        List<Task> tasks = findAll();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Code,Name,Status,RepUserCode"); // ヘッダー行を書き込み
            writer.newLine();
            for (Task task : tasks) {
                if (task.getCode() == updateTask.getCode()) {
                    task = updateTask; // 更新されたタスクを置き換え
                }
                writer.write(createLine(task));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * コードを基にタスクデータを削除します。
     * 
     * @param code 削除するタスクのコード
     */
    // public void delete(int code) {
    // try () {

    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }

    /**
     * タスクデータをCSVに書き込むためのフォーマットを作成します。
     * 
     * @param task フォーマットを作成するタスク
     * @return CSVに書き込むためのフォーマット文字列
     */
    // private String createLine(Task task) {
    // }
}