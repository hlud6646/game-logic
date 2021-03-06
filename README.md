**64**

Ramble from today (6/3/21)

Try to collect a heap of actions (ask around) and then try to abstract.
- Move a counter from a region to an adjacent region.
- Draw a line from one region to another.
- Draw a dot on a line.
- Rotate a token.
- Change the color of a square.
- Swap two sqaures.
- Move a monkey along a line.
- Join two regions and color the union red.
- Join two regions with a line which doesn't cross any other lines.

Let the tokens be Monkey, Elephant, Rat and Amoeba.
There should always be an implicit Ordering (or is it Ordered or something else?) so that 
things like scissors paper rock work. 

Since players alternate moving first in a match of 6 games, they can have different actions.
(Think fox and the hounds). This means that the inital board doesn't actually have to be that 
symmetrical. Left right symmetry will still be an aesthetic plus though.
On this note, gotta figure out the scoring. Probs need to wait till the end of the match 
and then call the winner, and calculate elo delta based on score.

Tokens can have a NESW orientation; that way an action might be to rotate a token.

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

type Links = List[Link] extends Property (will not compile)
case class Link(
  to: Region
  dotted: Boolean (for Sprouts like games)
  ??? anything else that a line could exhibit?
)




## Introduction / Concept.
A two player game, where the board layout and rules are randomly generated.
The game functions like online chess, with players taking turns to make a 
move on a square board.  A win buffs a player's ELO, a loss decreases it.
The game should include (as possible (albeit unlikely) realisations of rng rule sets)
- The fox and the hounds;
- Checkers;
- A simplified backgammon; 
- Sprouts?
- Dots and Boxes;
- Noughts and Crosses;

Hopefully most games will be meaningful, that is the rules can be deciphered from early 
play, and the more skillful player will win. There is a doubling cube (se 'Scoring'), which
will allow a player to gamble with their ELO.  This will hopefully introduce an element of 
bluff, intended to counteract the instances of games which suck.

The game is played on a (modified) 8x8 grid (hence the name). The reasons are: 
- I like chess;
- This shape is mobile friendly (chess with thumbs works perfectly).

## Scoring.
Playes have a persistant account, with an ELO rating starting at 1000 for a fresh account.
Since the first game will be spent experimenting, with little/no knowledge of the rules, 
when players are connected, they begin a match of 6 games. After each game the ELO shift is 
privately recorded; at the end of the 6 they are summed and the two player's ELOs are
modified.

Inspired by backgammon, there is a doubling cube. The ususal rules regarding its use apply. 
To be determined whether redoubles are allowed.  If so, they must be subject to the following 
condition. Player A has an ELO of 1024 and player B has an ELO of 600. Player A stands to 
gain 1 ELO point if they win the game. If they were to double 10 times, Player A would then 
stand to lose 1024 ELO points.  They should therefore not be allowed to double an eleventh 
time, to preven any player from going into negative ELO.

## Implementation.
Since the idea basically came up in the hunt for a meaningful Scala project for learning, 
Scala + play is the obvious choice.
The hard part will be finding the metarules that generate interesting games. The easy 
part should basically be everything else.

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
The board starts as an (uncoloured) 8x8 grid.
It is then messed with according to some combination of the following transformations:
B1: Regions are coloured using up to 4 colours;
B2: Regions are joined (effectively reducing the size of the board);
B3: Edges are associated; 
B4: Regions are blacked out (effectively removed);
B5: PLacing movable counters on the board;
(Question: Are these operations commutative?)
Since this is not chess, squares indicies are zero indexed.

### Action
Then the 'action' is decided. That is, what is the atomic change of state that 
a player can impart on the board? This will depend on the board, but some examples are 
A1: Click a square and change its color;
A2: Move a token to another square anywhere on the board;
A3: Move a token up to three places in a diagonal motion;
A4: Move a token [n, m] places in a motion in S, for S a subset of (Left Right Up Down)
A5: Draw a line from the center of one square to the center of an adjacent square; 

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
