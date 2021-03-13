**64**

Better datatype for tokens is a Map[Square: Seq[Token]];
Dots and lines need an owner; 
Bit much, but do we need a game validation engine to check if games are good before 
allowing players to begin?  If computationally gigantic that's still ok, since a stack of 
preapproved games coud be built during quiet time.

Game: Starting with weirdly grouped board, players join adjacent regions until no move is 
available.
Astraction: The action (join regions) monotonically decreases the metric (number of groups) until a certian value is reached (1).

Remove any non game logic from the 64 repo and 
Start structuring different repos; game logic, frontend, server, ... (reserve names)
tests?
go back to playing with randomly chosen semigroups -> write a (demo at least) Board.random method 
rename living.scala to match object;
methods/subclasses/objects in companion? whats the norm?
let a regoin hold all the properties; no need for data bag. 
shapeless for combining should be the combine one liner we are looking for.

## Introduction / Concept.
A two player game, where the board layout and rules are randomly generated.
The game functions like online chess, with players taking turns to make a 
move on a square board.  A win buffs a player's ELO, a loss decreases it.
The game should include (as possible (albeit unlikely) realisations of rule sets)
- The fox and the hounds;
- Checkers;
- A simplified backgammon; 
- Sprouts?
- Dots and Boxes;
- Noughts and Crosses;

Hopefully most games will be meaningful, that is the rules can be deciphered from early 
play, and the more skillful player will win. 

The game is played on a (modified) 8x8 grid (hence the name). The reasons are: 
- I like chess;
- This shape is mobile friendly (chess with thumbs works perfectly).

## Scoring
Games can be symmetrical (like checkers) or asymmetrical (like the fox and the hounds)
and so it is important that players play a match of (say) 6 games.  As with a chess match 
the result is then something in -3, -2.5, ..., 0, .5, ..., 3.
A player's ELO (or similar scoring system) is updated at the end of a match.

## Implementation.
Since the idea basically came up in the hunt for a meaningful Scala project for learning, 
Scala + Play will be the backend. Not sure yet for the front, mobile version etc.

Since players alternate moving first in a match of 6 games, they can have different actions.
(Think fox and the hounds). This means that the inital board doesn't actually have to be that 
symmetrical. Left right symmetry will still be an aesthetic plus though.
On this note, gotta figure out the scoring. Probs need to wait till the end of the match 
and then call the winner, and calculate elo delta based on score.

Triggers/callbacks to make things happen. E.g. 
Action: Move a token to another square.
Callback: If the destination already contains a token, delete it.
In other words, 'take a piece.'  Another example, steering towards Conway's GOL 
might be 'If a monkey is placed next to an elephant, it dies.' Probabaly these 
cutesy things (elephants can stomp on monkeys) should be built in.
Amoebas can mess with any other animal.
Rats can escape anything except amoebas.
Elephants can stomp rats. 
Nothing but an amoeba can even see an amoeba.

## UX.
### Color. 
Something neon/onedark.

### One Board.
See 'Tile Moving Games.'

### Tile Moving Games. 
On games where a player moves a tile by clicking it to select and clicking its destination, 
legal destinations should be lit up after the initial click.  Importantly, both players are 
able to see the exact same board (up to reflection) so that the inactive player reaps the 
benefit of the active player's experimentation. This helps both players learn the rules more 
quickly.

### Move Validations / Rule Learning.
Let the value of the metric (see 'Rules -> Metric') at the start of a player's turn be m_0. 
If, at the end of that player's turn the value of the metric is m_1 > m_0, 
then that player has made a 'good' move. Something (the border of the game?) should 
blink green to notify the players that this has occured. 

## Rules 
### Board
The board starts as an (uncoloured) 8x8 grid. It is then messed with according to transformations 
that color/join/put tokens on squares. 

### Action
Then the 'action' is decided. That is, what is the atomic change of state that 
a player can impart on the board? This will depend on the board, but some examples are 
- Move a token to another square anywhere on the board;
- Move a token up to three places in a diagonal motion;
- Move a token from a region to an adjacent region.
- Draw a line from one region to another.
- Draw a dot on a line.
- Rotate a token.
- Change the color of a square.
- Swap two sqaures.
- Move a monkey along a line.
- Join two regions and color the union red.
- Join two regions with a line which doesn't cross any other lines.

### Metric
A metric is chosen for the board. Examples: 
M1: (Integer) Number of green squares;
M2: (Integer) Number of tokens of type X on the board; 
M3: (Boolean) There are three adjacent Monkey tiles;
M4: (Rational) The ratio of green to red is exactly 1 : 2.

### Score
A player obtains a point if, over the course of their turn they increase the metric by some amount. 

### Outcome
The outcome is reached when a player's score reaches a certain value.








## Development Plan.

### Proof of Concept.
A board/rule generating method that generates a reasonable proportion of meaningful
/interesting games.

### Minimal Viable Product.
A desktop browser version where two players on a local network can play.

## Colaborators. 
Joel  -> Design? 
Woods -> Design? Dev?

## Todos.
- Look at other pencil and paper games for inspiration in drafting metarules.
- Mockup some board visualisations; 
- design rule generation (pen and paper);
- implement sketch of rule generation;
