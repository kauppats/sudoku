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

(defn valid-values-for [board coord]
  nil)

(defn filled? [board]
  nil)

(defn rows [board]
  nil)

(defn valid-rows? [board]
  nil)

(defn cols [board]
  nil)

(defn valid-cols? [board]
  nil)

(defn blocks [board]
  nil)

(defn valid-blocks? [board]
  nil)

(defn valid-solution? [board]
  nil)

(defn set-value-at [board coord new-value]
  nil)

(defn find-empty-point [board]
  nil)

(defn solve [board]
  nil)
