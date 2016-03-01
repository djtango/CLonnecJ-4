# clonnecj-4

This is a work-in-progress project featuring a connect 4 built in Clojure. The project was completely test-driven using the Speclj framework.
v1.0 is now live and features a two-player CLI.

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
