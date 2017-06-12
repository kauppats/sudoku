(ns sudoku
  (:require [clojure.set :as set]))

(def board identity)

(defn value-at [board coord]
  (get-in board coord)
)

(defn has-value? [board coord]
  (> (value-at board coord) 0)
)

(defn row-values [board [row _]]
  (set (get board row))
)

(defn col-values [board [_ col]]
  (set
    (for [row board] (get row col))
  )
)

(defn coord-pairs [coords]
  (for [row coords col coords] [row col])
)

(defn block-top-left [[row col]] [(* (quot row 3) 3) (* (quot col 3) 3)])

(defn block-values [board coord]
  (let [[block-row block-col] (block-top-left coord)
       ]
    (set
      (for [[row col] (coord-pairs (range 3))]
        (value-at board [(+ block-row row) (+ block-col col)])
      )
    )
  )
)

(def all-values #{1 2 3 4 5 6 7 8 9})

(defn valid-values-for [board coord]
  (if (has-value? board coord)
    #{}
    (let [col-vals (col-values board coord)
          row-vals (row-values board coord)
          block-vals (block-values board coord)
          all-vals (clojure.set/union col-vals row-vals block-vals)
         ]
      (clojure.set/difference all-values all-vals)
    )
  )
)

(defn filled? [board]
  (let [row-sets (for [row board] (set row))
        all-vals (apply clojure.set/union row-sets)
       ]
    (not (contains? all-vals 0))
  )
)

(defn rows [board]
  (for [index (range 9)]
    (set (row-values board [index 0]))
  )
)

(defn valid-sets? [a-sets]
  (every? (fn [a-seq] (= all-values a-seq)) a-sets)
)

(defn valid-rows? [board]
  (valid-sets? (rows board))
)

(defn cols [board]
  (for [index (range 9)]
    (set (col-values board [0 index]))
  )
)

(defn valid-cols? [board]
  (valid-sets? (cols board))
)

(defn blocks [board]
  (for [row (range 3) col (range 3)]
    (set (block-values board [(* row 3) (* col 3)]))
  )
)

(defn valid-blocks? [board]
  (valid-sets? (blocks board))
)

(defn valid-solution? [board]
  (and
    (valid-rows? board)
    (valid-cols? board)
    (valid-blocks? board)
  )
)

(defn set-value-at [board coord new-value]
  (assoc-in board coord new-value)
)

(defn find-empty-point [board]
  (let [empty-cell? (fn [coord] (not (has-value? board coord)))
        all-cells (coord-pairs (range 9))
        empty-cells (filter empty-cell? all-cells)
       ]
    (first empty-cells)
  )
)


(defn solve-helper [board]
  (if (filled? board)
    (if (valid-solution? board)
      [board]
      []
    )
    (let [empty-point (find-empty-point board)
         ]
      (for [value (valid-values-for board empty-point)
            solution (solve-helper (set-value-at board empty-point value))
           ]
        solution
      )
    )
  )
)

(defn solve [board]
  (first (solve-helper board))
)

