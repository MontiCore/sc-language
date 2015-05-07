package de.monticore.umlsc.parser;

(...) <<default_invariant = "OCL">>
statechart Variants {

  (c) initial state s1 {
    Java:[a==5];
    entry / {System.out.println("EntryAction");} Java:[a.assertEquals(5)];
    do Java:[d] / {System.out.println("DoAction");}
    exit / {System.out.println("ExitAction");}
  }

  (c) final state s2;
  
  (c,...) <<state_specific_stereotypes>> state s3 {
    Java:[c];
    state sub1;
    final state sub2;
    <<intern_transition>> -> [true] internmethod() / {log("intern");} [true];
    sub1 -> sub2 : Java:[b==3];
  }

  (c) state s4;
  
  state s5 {->: return(a==5);}
  
  state s6 {
    ->: [true] internmethod() / {log("intern");} [true];
  }

  <<transition_specific_stereotypes>> s1 -> s3 : event1();
  s3 -> s2 : Java:[para1 >= 2] event2(para1, "hello") / {System.out.println("s3->s2");} Java:[event=="postcondition"];
  s1 -> s3 : event3(...);
  s1 -> s4 : / {a++;} Java:[a>0];
  s1 -> s4 : / {a++;}
  s4 -> s2;
  s4 -> s2 : return;
  s4 -> s2 : return / {System.out.println("s4->s2");}
  s4 -> s2 : return ...;
  s4 -> s2 : return (1+3);
  s4 -> s2 : return (1+4) / {System.out.println("s4->s2");}

  code{
    System.out.println("This is addional code!");
  }
}