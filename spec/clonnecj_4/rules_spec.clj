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
      (should-throw java.lang.Exception "Error: a column cannot accept more than 6 marks"
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
  (with-all! board2 [[:x :x :x] [:x :x] [:o :x] [:o :o :x :o] [:o] [] []])
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
               (max-horizontal-score @board2 :o))))
