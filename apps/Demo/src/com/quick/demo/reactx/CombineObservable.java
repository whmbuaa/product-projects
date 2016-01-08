package com.quick.demo.reactx;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func2;
import rx.functions.FuncN;
import rx.schedulers.Schedulers;

public class CombineObservable {

	public static Observable<Integer> createObservable(final int index){
		return Observable.create(new Observable.OnSubscribe<Integer>() {

			@Override
			public void call(Subscriber<? super Integer> subscriber) {
				// TODO Auto-generated method stub
				for(int i= 1; i < 6 ; i++){
					subscriber.onNext(i*index);
				}
				try{
					Thread.sleep(2000);
					
				}
				catch(Exception e){
					
				}
			}
		}).subscribeOn(Schedulers.newThread());
	}
	
	public static Observable<Integer> combineLatestObservable(){
		
		return Observable.combineLatest(createObservable(1), createObservable(10), new Func2<Integer,Integer,Integer>(){

			@Override
			public Integer call(Integer num1, Integer num2) {
				// TODO Auto-generated method stub
				return num1+num2;
			}
			
			
		});
		
	}
	
	public static Observable<Integer> combineListObservable(){
		List<Observable<Integer>> list = new ArrayList<>();
		for(int i = 0; i < 5; i++){
			list.add(createObservable(i));
		}
		return Observable.combineLatest(list, new FuncN<Integer>() {

			@Override
			public Integer call(Object... args) {
				// TODO Auto-generated method stub
				int temp = 0 ;
				for(Object i : args){
					temp += (Integer)i;
				}
				return temp;
			}
		});
	}
}
