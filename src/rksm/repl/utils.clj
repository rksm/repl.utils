(ns rksm.repl.utils
  (:require [leiningen.core.main :as lein]))

(defmacro lein [& args]
  `(try
     (binding [lein/*exit-process?* false]
       (lein/-main ~@(map str args)))
     (catch clojure.lang.ExceptionInfo e#
       (when-not (= "Suppressed exit" (.getMessage e#))
         (when-not (:exit-code (ex-data e#)) (throw e#))))))

(defn all-interns []
  (->> (all-ns)
       (mapcat ns-interns)
       (map val)
       (sort #(compare (str %1) (str %2)))
       (map #(str (->> % .ns ns-name) "/" (.sym %) " " (str (:arglists (meta %)))))))

(defn- ensure-re
  [re-or-string]
  (if (string? re-or-string)
    (re-pattern (str ".*" re-or-string ".*"))
    re-or-string))

(defn search-for-symbol
  [& symbols-or-regexps]
  (let [regexps (map ensure-re symbols-or-regexps)]
   (filter
    (fn [string] (every? #(re-matches % string) regexps))
    (all-interns))))

(comment
  (lein install)
  (lein clique)
  (sh "ls")
  (require '[clojure.java.shell :refer [sh]])
  (sh "ls")
  (ns-unmap (find-ns 'clojure.core) 'lein)
  (ns-unmap (find-ns 'rksm.repl.utils-test) 'search-for-symbol)
  (search-for-symbol #".*re-ma.*")
  (type #".*re-ma.*")
  (map #(if (string? %) (re-pattern %) %))
  (re-pattern "\\d+")
  (= (str (re-pattern "\\d+")) (str (re-pattern #"\\d+")))
  )
