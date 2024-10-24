package com.taskapp.logic;

import java.time.LocalDate;
import java.util.List;

import com.taskapp.dataaccess.LogDataAccess;
import com.taskapp.dataaccess.TaskDataAccess;
import com.taskapp.dataaccess.UserDataAccess;
import com.taskapp.exception.AppException;
import com.taskapp.model.Log;
import com.taskapp.model.Task;
import com.taskapp.model.User;

public class TaskLogic {
    private final TaskDataAccess taskDataAccess;
    private final LogDataAccess logDataAccess;
    private final UserDataAccess userDataAccess;


    public TaskLogic() {
        taskDataAccess = new TaskDataAccess();
        logDataAccess = new LogDataAccess();
        userDataAccess = new UserDataAccess();
    }

    /**
     * 自動採点用に必要なコンストラクタのため、皆さんはこのコンストラクタを利用・削除はしないでください
     * @param taskDataAccess
     * @param logDataAccess
     * @param userDataAccess
     */
    public TaskLogic(TaskDataAccess taskDataAccess, LogDataAccess logDataAccess, UserDataAccess userDataAccess) {
        this.taskDataAccess = taskDataAccess;
        this.logDataAccess = logDataAccess;
        this.userDataAccess = userDataAccess;
    }

    /**
     * 全てのタスクを表示します。
     *
     * @see com.taskapp.dataaccess.TaskDataAccess#findAll()
     * @param loginUser ログインユーザー
     */
    //設問2
    public void showAll(User loginUser) {

        if (loginUser == null) {
            
            System.out.println("ログインユーザー情報が必要です。");
            return;
        }

        List<Task> tasks = taskDataAccess.findAll();

        if (tasks == null || tasks.isEmpty()) {

            System.out.println("タスクが見つかりません。");
            return;
        }

        int count = 3;
        for (Task task : tasks) {
            String status = "";
            switch (task.getStatus()) {
                case 0:
                    status = "未着手";
                    break;
                case 1:
                    status = "着手中";
                    break;
                case 2:
                    status = "完了";
                    break;
                case 3:
                    status = "不明";
                    break;
            }

            User taskUser = userDataAccess.findByCode(task.getRepUser().getCode());
            String userDisplay;
            if (taskUser.getCode() == loginUser.getCode()) {
                userDisplay = "あなたが担当しています";
            } else {
                userDisplay = taskUser.getName() + "が担当しています";
            }

            System.out.println("タスク名：" + task.getName() + ", 担当者名：" + userDisplay + ", ステータス：" + status);
        }
    }


    /**
     * 新しいタスクを保存します。
     *
     * @see com.taskapp.dataaccess.UserDataAccess#findByCode(int)
     * @see com.taskapp.dataaccess.TaskDataAccess#save(com.taskapp.model.Task)
     * @see com.taskapp.dataaccess.LogDataAccess#save(com.taskapp.model.Log)
     * @param code タスクコード
     * @param name タスク名
     * @param repUserCode 担当ユーザーコード
     * @param loginUser ログインユーザー
     * @throws AppException ユーザーコードが存在しない場合にスローされます
     */
    public void save(int code, String name, int repUserCode,
                    User loginUser) throws AppException {
        User repUser = userDataAccess.findByCode(repUserCode);
    if (repUser == null) {
        throw new AppException("存在するユーザーコードを入力してください");
    }
    Task task = new Task(code, name, 0, repUser);
    taskDataAccess.save(task);
        
    Log log = new Log(code, loginUser.getCode(), 0, LocalDate.now());
    logDataAccess.save(log);
    }

    /**
     * タスクのステータスを変更します。
     *
     * @see com.taskapp.dataaccess.TaskDataAccess#findByCode(int)
     * @see com.taskapp.dataaccess.TaskDataAccess#update(com.taskapp.model.Task)
     * @see com.taskapp.dataaccess.LogDataAccess#save(com.taskapp.model.Log)
     * @param code タスクコード
     * @param status 新しいステータス
     * @param loginUser ログインユーザー
     * @throws AppException タスクコードが存在しない、またはステータスが前のステータスより1つ先でない場合にスローされます
     */
    public void changeStatus(int code, int status,
                            User loginUser) throws AppException {
        Task task = taskDataAccess.findByCode(code);
        if (task == null) {
            throw new AppException("存在するタスクコードを入力してください");
        }
        if (status < 0 || status > 2) {
            throw new AppException("ステータス1・2の中から選択してください");
        }
    
    int taskStatus = task.getStatus();

    // ステータス変更の条件を確認
    if (taskStatus == 0) { // タスクが「未着手」の場合
        if (status != 1) { // 新しいステータスが「着手中」以外の場合
            throw new AppException("ステータスは、前のステータスより1つ先のもののみ選択してください");
        }
    } else if (taskStatus == 1) { // タスクが「着手中」の場合
        if (status != 2) { // 新しいステータスが「完了」以外の場合
            throw new AppException("ステータスは、前のステータスより1つ先のもののみ選択してください");
        }
    } else if (taskStatus == 2) { // タスクが「完了」の場合
        throw new AppException("ステータスは、前のステータスより1つ先のもののみ選択してください");
    }

        task.setStatus(status); // ステータスを更新
        taskDataAccess.update(task); // タスクデータを更新


        Log log = new Log(task.getCode(), loginUser.getCode(), status, LocalDate.now());
        logDataAccess.save(log);
        }


    /**
     * タスクを削除します。
     *
     * @see com.taskapp.dataaccess.TaskDataAccess#findByCode(int)
     * @see com.taskapp.dataaccess.TaskDataAccess#delete(int)
     * @see com.taskapp.dataaccess.LogDataAccess#deleteByTaskCode(int)
     * @param code タスクコード
     * @throws AppException タスクコードが存在しない、またはタスクのステータスが完了でない場合にスローされます
     */
    // public void delete(int code) throws AppException {
    // }
    }