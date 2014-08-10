(ns rksm.repl.utils
  (:require [leiningen.core.main :as lein]
            [io.aviso.exception :as ex]
            [clojure.tools.trace :as t]))

;; -=-=-=-=-
;; leiningen
;; -=-=-=-=-

(defmacro lein [& args]
  "Run lein from within the clojure runtime. Like vinyasa.lein, however catches
  the suppressed exit exception."
  `(try
     (binding [lein/*exit-process?* false]
       (lein/-main ~@(map str args)))
     (catch clojure.lang.ExceptionInfo e#
       (when-not (= "Suppressed exit" (.getMessage e#))
         (when-not (:exit-code (ex-data e#)) (throw e#))))))

;; -=-=-=-=-=-=-=-=-=-
;; runtime inspection
;; -=-=-=-=-=-=-=-=-=-

(defn all-interns []
  "Gather strings for all interns of all namespaces. Useful when searching for
  functions / interfaces."
  (->> (all-ns)
       (mapcat ns-interns)
       (map val)
       (sort #(compare (str %1) (str %2)))
       (map #(str (->> % .ns ns-name)
                  "/" (.sym %) " "
                  (->> % meta :arglists str)))))

(defn- ensure-re
  [re-or-string]
  (if (string? re-or-string)
    (re-pattern (str ".*" re-or-string ".*"))
    re-or-string))

(defn search-for-symbol
  "Search for the provided strings and regexps in `all-interns`. All
  strings/regexps must match (logical and)."
  [& symbols-or-regexps]
  (let [regexps (map ensure-re symbols-or-regexps)]
   (filter
    (fn [string] (every? #(re-matches % string) regexps))
    (all-interns))))

;; -=-=-=-=-
;; debugging
;; -=-=-=-=-

(defn get-stack
  [& [depth]]
  (let [stack (drop 1 (.getStackTrace (Thread/currentThread)))]
    (if depth (take depth stack) stack)))

(defn print-stack
  [& [depth stack]]
  (doseq [e (or stack (get-stack depth))] (println e)))

(defn dumb-stack
  [& [message]]
  (#'ex/write-exception (Throwable. (or message "-= dumbed stack =-"))))

;; -=-=-=-
;; tracing
;; -=-=-=-

(defn traced-fn
  [f & [name]]
  (with-meta
    (fn [& args] (t/trace-fn-call name f args))
    (meta f)))

;; -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

(comment
  (require 'vinyasa.inject)
  (vinyasa.inject/in
   clojure.core
   [rksm.repl.utils lein search-for-symbol get-stack print-stack dumb-stack traced-fn])
  (lein install)
  (lein localrepo coords "target/repl.utils-0.1.0.jar")
  (lein localrepo install "target/repl.utils-0.1.0.jar" "repl.utils/repl.utils" "0.1.0")
  (lein deploy clojars)
  )
