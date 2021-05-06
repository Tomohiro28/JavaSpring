package com.exmple.demo.models;

import javax.validation.GroupSequence;
//@GroupSequence　バリエーショングループの実行順序設定
@GroupSequence({ValidGroup1.class,ValidGroup2.class,ValidGroup3.class})
public interface GroupOrder {

}
