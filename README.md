# clonnecj-4

This is a work-in-progress project featuring a connect 4 built in Clojure. The project was completely test-driven using the Speclj framework.
v1.0 is now live and features a two-player CLI.

## Design:
The current build features three mainspaces: `core, rules, board-presenter`. Core serves as the main entry point for the program to run, whilst rules looks after the game engine and board-presenter prepares the board for printing into the command line.

Traditional connect 4 uses a 7x6 grid, and the `board` data structure was built to reflect this. The base data-structure `(board/empty-board)` is a 7x0 vector of vectors: `[[] [] [] [] [] [] []]`. Transposing the base 2D vector of vectors to represent columns of rows (as opposed to the more conventional rows of columns) allows for a much simpler implementation for "dropping" marks into a column - you can simply `conj` them onto a given column vector and your mark will be in the right position and order.

This data structure made it easy to calculate the score in a column and by using grid transformations, one base scoring function `max-vertical-score` could be used: simply transposing the board into rows of columns or diagonalizing it before passing that through the `max-vertical-score`.src/clonn 

## Known Issues:
Trying to input a column index for a full column results in a `java.lang.UnsupportedOperationException` error.

## Installation

To run this app, you will need to have <a href="https://github.com/technomancy/leiningen/">Leiningen</a> and <a href="http://git-scm.com/">git</a> installed:

1. Clone the repository with git: `git clone git://github.com/djtango/CLonnecJ-4.git`

2. Install Leiningen (version 2.x)
  a. Download the lein script: @wget https://github.com/technomancy/leiningen/raw/preview/bin/lein@
     (use <a href="https://github.com/technomancy/leiningen/raw/preview/bin/lein.bat">lein.bat</a> on Windows)
  b. Place it on your path and chmod it to be executable: @chmod +x lein@
  c. Run: @lein self-install@
3. To run the tests, enter `lein with-profile test spec` into your terminal.

4. Execute `lein run` staying in the clonnecj-4 directory - this will download all necessary dependencies.
