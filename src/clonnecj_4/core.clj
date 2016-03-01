(ns clonnecj-4.core
  (:require [clonnecj-4.rules :as rules]
            [clonnecj-4.board-presenter :as bp]))

(defn welcome-screen
  []
  (println "Welcome to Clonnecj-4 a Connect 4 CLI game engine run on Clojure")
  (println "Let's get started! Please take it in turns to select a column 1-7 to drop your mark.")
  (println "Player 1 starts and uses :o's, Player 2 uses :x's")
  (println "C")
  (println " l")
  (println "  o")
  (println "   n")
  (println "    n")
  (println "     e")
  (println "      j")
  (println "       -")
  (println "        4")
  (println "       !")
  (println "      !")
  (println "     !")
  (println "    !")
  (println "   !")
  (println "  !")
  (println " !")
  (println "!")
  (println "Current board:")
  (bp/print-board bp/empty-board))


(defn allowed
  [player-input]
  (and (= (type player-input)
          java.lang.Long)
       (> 8 player-input)
       (<= 1 player-input)))

(defn get-column
  [current-player]
  (let [player-input (do (print (str "Player " current-player ": "))
                         (flush)
                         (->> (read-line)
                              read-string))]
    (if (allowed player-input)
      (dec player-input)
      (do (println "Error: please enter a value between 1-7.")
          (get-column current-player)))))

(defn select-column
  [current-player mark board]
  (or (try (rules/select-column (get-column current-player)
                                mark
                                board)
           (catch Exception e (str (.getMessage e))))
      (recur current-player
             mark
             board)))
(defn -main
  []
  (welcome-screen)
  (loop [[current-player & next-player] (cycle [1 2])
         board bp/empty-board]
    (println "Please select your column (1-7)")
    (let [mark (get {1 :o 2 :x} current-player)
          ;; current-choice (get-column current-player)
          new-board (select-column current-player
                                   mark
                                   board)]
      (println (str "Current board: " (bp/print-board new-board)))
      (bp/print-board new-board)
      (if (>= (rules/calculate-score new-board mark)
              4)
        (println (str "Congratulations, Player " current-player " wins!"))
        (recur next-player new-board)))))
