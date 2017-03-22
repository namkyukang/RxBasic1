package com.customview.kang.rxbasic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    TextView text1,text2,text3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 실제 Task 처리하는 객체 ( 발행자 )!!!!

        Observable<String> simpleObservable =
                Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        // 네트웍을 통해서 데이터를 긁어온다
                        // 반복문을 돌면서 -----------------------
                        // for (네트웍에서 가져온 데이터) { json
                        subscriber.onNext("Hello RxAndroid !!");    //옵저버들을 호출.
                        // }
                        // --------------------------------------
                        subscriber.onCompleted();
                    }
                });
        //옵저버(rnehrwk)를 등록해주는 함수
        simpleObservable
                .subscribe(new Subscriber<String>() {//observer(구독자)
                    //콜백함수를 하나의 객체에
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "[observer1]complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "[observer1]error: " + e.getMessage());
                    }

                    @Override
                    public void onNext(String text) {
                        Toast.makeText(MainActivity.this, "[observer1]"+text, Toast.LENGTH_SHORT).show();
                    }
                });
        //옵저버를 등록하는 함수 - 진화형(각 함수를 하나의 콜백객체에 나눠서 담아준다)
        simpleObservable.subscribe(new Action1<String>() {//onNext 함수와 동일한 역활을 하는 콜백 객체
            @Override
            public void call(String s) {
                Toast.makeText(MainActivity.this, "[observer1]"+s, Toast.LENGTH_SHORT).show();
            }
        }, new Action1<Throwable>() {   //에러가 났을때 던져주는 객체. onError 함수와 동일한 역활을 하는 콜백 객체
            @Override
            public void call(Throwable throwable) {
                Log.e(TAG, "[observer1]error " + throwable.getMessage());
            }
        }, new Action0() {// onComplete와 동일한 역활을 하는 콜백객체
            @Override
            public void call() {
                Log.e(TAG, "[observer1]comlete ");
            }
        });
        // 옵저버를 등록하는 함수 - 최종진화형(람다식)
        simpleObservable.subscribe(
                (string) -> {Toast.makeText(MainActivity.this, "[Observer3] "+string, Toast.LENGTH_SHORT).show();}
                ,(error) -> {Log.e(TAG, "[Observer2] error: " + error.getMessage());}
                ,() -> {Log.d(TAG, "[Observer2] complete");}
        );
    }
}
