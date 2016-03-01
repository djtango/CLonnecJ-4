(ns clonnecj-4.board-presenter
  (:require [clonnecj-4.rules :as rules]))

(def empty-board
  [[] [] [] [] [] [] []])
(defn wrap-cells
  [board]
  (map (fn [row]
         (map (fn [cell] [cell])
              row))
       board))

(defn prettify
  [board]
  (let
    [empty-count (- 6 (count (first board)))
     padding (take empty-count (repeat "  "))]
      (->> board
           (#(assoc-in % [0] (concat (first %) padding)))
           (rules/pad-transpose "  ")
           wrap-cells)))

(defn print-board
  [board]
  (->> board
       prettify
       reverse
       (#(doseq [row %] (println row)))))
