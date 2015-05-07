package de.monticore.umlsc.parser;

statechart SingleState {

  (c) state AuctionOpen {
    Java:[timePol.status == TimingPolicy.RUNNING];
    entry / {protocol("Auction "+auctionIdent+": "+title+"started.");}
    do / {protocol("Auction running "+Time.now());}
    exit / {protocol("Auction "+auctionIdent+" finished.");}
  }
}