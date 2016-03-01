(ns clonnecj-4.rules-spec
  (:require [speclj.core :refer :all]
            [clonnecj-4.rules :refer :all]))

(describe
  "Populating a given column:"
  (it "when selecting a column, the mark should populate the lowest unfilled cell by appending the mark to the end of the column"
      (should= [:x :x :o :x]
               (-> []
                   (drop-mark-in-column :x)
                   (drop-mark-in-column :x)
                   (drop-mark-in-column :o)
                   (drop-mark-in-column :x))))

  (it "a column will not allow more than 6 marks in a column"
      (should-throw java.lang.Exception "Error: a column cannot accept more than 6 marks. Please select another column."
                    (drop-mark-in-column [:x :o :x :o :x :o]
                                         :x))))

(describe
  "Choosing a column:"
  (with! empty-board [[] [] [] [] [] [] []])
  (it "when passed a number, the game will populate the right column"
      (should= [[] [] [] [:x :o :x] [:o] [] []]
               (->> @empty-board
                    (select-column 4 :o)
                    (select-column 3 :x)
                    (select-column 3 :o)
                    (select-column 3 :x)))))

(describe
  "Calculating the score:"
  (with-all! board [[:x :x :x] [:o :o] [] [] [] [] []])
  (with-all! board2 [[:x :x :x] [:x :o] [:o :x] [:o :o :x :o] [:o] [] []])
  (with-all! board3 [[:x :o :x] [:o :x] [:x] [] [] [] []])
  (with-all! board4 [[] [] [:x] [:x :o :x] [:x :x :o] [] []])
  (with-all! board4 [[] [] [:x] [:x :o :x] [:x :x :o] [] []])
  (with-all! board5 [[] [:x :x] [:o :x] [] [:o] [:x :o] [:o :x :o]])
  (it "Calculates score correctly for 'x' in a given column"
      (should= 3
               (max-vertical-score @board :x)))
  (it "Calculates score correctly for 'o' in a given column"
      (should= 2
               (max-vertical-score @board :o)))
  (it "Calculates the score for many columns and returns the max"
      (should= 3
               (max-vertical-score @board2 :x)))
  (it "Returns the max score within a given column"
      (should= 2
               (max-vertical-score @board2 :o)))
  (it "Calculates the score correctly for 'x' in a given row"
      (should= 2
               (max-horizontal-score @board2 :x)))
  (it "Calculates the score correctly for 'o' in a given row"
      (should= 3
               (max-horizontal-score @board2 :o)))
  (it "Calculates the score correctly for 'x' along the positive diagonal"
      (should= 3
               (max-diagonal-score (pad-transpose :empty @board3) :x)))
  (it "Calculates the score correctly for 'o' along the positive diagonal"
      (should= 2
               (max-diagonal-score (pad-transpose :empty @board3) :o)))
  (it "Calculates the score correctly for 'x' along the negative diagonal"
      (should= 2
               (max-diagonal-score (pad-transpose :empty @board4) :x)))
  (it "Calculates the score correctly for 'o' along the negative diagonal"
      (should= 2
               (max-diagonal-score (pad-transpose :empty @board4) :o))))

(describe
  "diagonalizing the board"
  (with-all! square-grid [[:a :b :c] [:d :e :f] [:g :h :i]])
  (it "#map-coordinates appends the row and column index onto the existing values"
      (should= '(([:a [0 0]] [:b [1 0]] [:c [2 0]])
                 ([:d [0 1]] [:e [1 1]] [:f [2 1]])
                 ([:g [0 2]] [:h [1 2]] [:i [2 2]]))
               (map-coordinates @square-grid)))
  (it "#group-by-diagonals groups lines along +ve axes"
      (should= {0 [[:a [0 0]]],
                1 [[:b [1 0]] [:d [0 1]]],
                2 [[:c [2 0]] [:e [1 1]] [:g [0 2]]],
                3 [[:f [2 1]] [:h [1 2]]],
                4 [[:i [2 2]]]}
               (group-by-diagonals @square-grid +)))
  (it "#group-by-diagonals groups lines along -ve axes"
      (should= {-1 [[:d [0 1]] [:h [1 2]]],
                -2 [[:g [0 2]]],
                 0 [[:a [0 0]] [:e [1 1]] [:i [2 2]]],
                 1 [[:b [1 0]] [:f [2 1]]],
                 2 [[:c [2 0]]]}
               (group-by-diagonals @square-grid -)))
  (it "#drop-coordinates returns just cell values without coordinates for +ve diagonals"
      (should= '((:a)
                 (:b :d)
                 (:c :e :g)
                 (:f :h)
                 (:i))
               (drop-coordinates (group-by-diagonals @square-grid +))))
  (it "#drop-coordinates returns just cell values without coordinates for -ve diagonals"
      (should= '((:a :e :i)
                 (:b :f)
                 (:c)
                 (:d :h)
                 (:g))
               (drop-coordinates (group-by-diagonals @square-grid -)))))
