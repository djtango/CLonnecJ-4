(ns clonnecj-4.rules)

(defn drop-mark-in-column
  [column mark]
  (if (>= (count column) 6)
    (throw (Exception. "Error: a column cannot accept more than 6 marks. Please select another column."))
    (conj column mark)))

(defn select-column
  [column-index mark board]
  (assoc-in board
            [column-index]
            (drop-mark-in-column (nth board column-index)
                                 mark)))

(defn max-consecutive-marks
  [mark coll]
  (letfn [(running-total
            [coll counter longest-string]
            (if (seq coll)
              (if (= (first coll) mark)
                (running-total (rest coll) (inc counter) (max (inc counter) longest-string))
                (running-total (rest coll) 0 (max counter longest-string)))
              longest-string))]
    (running-total coll 0 0)))

(defn max-vertical-score
  [board mark]
  (apply max (map #(max-consecutive-marks mark %) board)))

(defn pad-transpose
  [default coll-2d]
  (let [seqs (map seq coll-2d)]
    (when (some identity seqs)
      (cons (map first (map (fnil identity `(~default)) seqs))
            (pad-transpose default (map rest seqs))))))

(defn map-coordinates
  [board]
  (map-indexed (fn [col-idx column]
                 (map-indexed (fn [row-idx row]
                                [row [row-idx col-idx]])
                              column))
               board))

(defn one-level-flatten
  [coll]
  (apply concat coll))

(defn group-by-diagonals
  [board direction]
  (->> board
       map-coordinates
       one-level-flatten
       (group-by #(reduce direction (second %)))))

(defn drop-coordinates
  [diagonalized-board]
  (->> diagonalized-board
       vals
       (map #(map first %))))

(defn diagonalize
  [board direction]
  (->> (group-by-diagonals board direction)
       drop-coordinates))

(defn max-horizontal-score
  [board mark]
  (let [transposed-board (pad-transpose :empty board)]
    (max-vertical-score transposed-board mark)))

(defn max-diagonal-score
  [board mark]
  (let [diag-board-pos (diagonalize board +)
        diag-board-neg (diagonalize board -)]
    (max (max-vertical-score diag-board-pos mark)
         (max-vertical-score diag-board-neg mark))))

(defn calculate-score
  [board mark]
  (max (max-vertical-score board mark)
       (max-horizontal-score board mark)
       (max-diagonal-score board mark)))
