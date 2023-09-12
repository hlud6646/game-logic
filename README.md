# game


This repo contains the underlying game logic, which can be broken down
to the following sections:

1. Generate a board;
2. Choose an action that is compatible with the board;
3. Choose a metric & win condition compatible with these; 
4. Bundle these together and provide an API for recieving and validating
   moves.

The board logic is in the package called 'board'. The main routine is the fromChain
method in the Board object which takes a sequence of Board => Board functions and 
applies them to a new board, allowing an expressive syntax like
```Scala
   Board.fromChain(List(
    color(Color.Red, 0), 
    symD(color(Color.Red, 3)),
    repeat(nTimes=4, startIndex=10, step=3)(color(Color.Red))
   ))
```
- Move a token to another square anywhere on the board;
- Move a token up to three places in a diagonal motion;
- Flip a token
- Move a token from a region to an adjacent region.
- Draw a line from one region to another.
- Draw a dot on a line.
- Rotate a token.
- Change the color of a square.
- Swap two sqaures.
- Move a monkey along a line.
- Join two regions and color the union red.
- Join two regions with a line which doesn't cross any other lines.
