(ns clonnecj-4.rules)

(defn drop-mark-in-column
  [column mark]
  (if (>= (count column) 6)
    (throw (Exception. "Error: a column cannot accept more than 6 marks"))
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

(defn extend-tuple
  [default & colls]
  (lazy-seq
    (let [seqs (map seq colls)]
      (when (some identity seqs)
        (cons (map (fnil first default) seqs)
              (apply extend-tuple default (map rest seqs)))))))

(defn max-horizontal-score
  [board mark]
  (let [explicit-empty-board (extend-tuple :empty board)
        transposed-board (apply map vector explicit-empty-board)]
    (max-vertical-score transposed-board mark)))

