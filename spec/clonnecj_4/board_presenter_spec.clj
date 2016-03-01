(ns clonnecj-4.board-presenter-spec
  (:require [speclj.core :refer :all]
            [clonnecj-4.board-presenter :refer :all]))

(describe
  "Prettifying the board:"
  (with-all! board [[:x :x :x] [:o :o] [] [] [] [] []])
  (it "when given a board, the prettifier wraps each cell in [  ]s and transposes the board"
      (should= [[[ :x ][ :o ]["  "]["  "]["  "]["  "]["  "]]
                [[ :x ][ :o ]["  "]["  "]["  "]["  "]["  "]]
                [[ :x ]["  "]["  "]["  "]["  "]["  "]["  "]]
                [["  "]["  "]["  "]["  "]["  "]["  "]["  "]]
                [["  "]["  "]["  "]["  "]["  "]["  "]["  "]]
                [["  "]["  "]["  "]["  "]["  "]["  "]["  "]]]
               (prettify @board))))

