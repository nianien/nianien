package com.nianien.test.generic;

import com.nianien.core.reflect.Generics;

import java.util.List;

/**
 * @author scorpio
 * @version 1.0.0
 */
public class LevelImpl<T> implements ILevelSecond<Character, T, List<Boolean>> {

  public static void main(String[] args) {

    System.out.println(Generics.find(LevelImpl.class, ILevelSecond.class, 0));
    System.out.println(Generics.find(LevelImpl.class, ILevelSecond.class, 1));
    System.out.println(Generics.find(LevelImpl.class, ILevelSecond.class, 2));
    System.out.println(Generics.find(LevelImpl2.class, LevelImpl.class, 0));
    System.out.println(Generics.find(LevelImpl2.class, ILevel.class, 0));
    System.out.println(Generics.find(LevelImpl.class, ILevel.class, 0));
    System.out.println(Generics.find(LevelImpl.class, ILevelFirst.class, 0));
    System.out.println(Generics.find(LevelImpl.class, ILevelFirst.class, 1));

  }


  static class LevelImpl2 extends LevelImpl<String> {

  }


}
