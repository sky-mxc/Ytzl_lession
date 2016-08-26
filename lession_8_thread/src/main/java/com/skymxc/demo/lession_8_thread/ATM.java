package com.skymxc.demo.lession_8_thread;


import android.util.Log;

/**
 * Created by sky-mxc
 */
public class ATM  extends  Thread{

    private Account account ;

    private int money ;


    /**
     * 取钱
     * @param money 数目
     * @param account 账户  同步
     * @return
     */
    private int takeMoney(int money, Account account){

        synchronized (account) {
            int accountMoney = account.getMoney();

            try {
                sleep(500);

                if (money <= accountMoney) {

                    account.setMoney(accountMoney - money);
                    sleep(500);
                    return money;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public void takeMoney(int money){
        this.money=money;
    }

    @Override
    public void run() {
      int m=  takeMoney(money,account);

        if (m >0){
            Log.e("Tag",getName()+"==取走了"+m);
        }else{
            Log.e("Tag",getName()+"==余额不足");
        }
        Log.e("Tag","账户余额："+account.getMoney());
    }

    public ATM(String name) {
        super(name);
    }


    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
