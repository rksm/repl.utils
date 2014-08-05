(ns rksm.repl.utils-test
  (:require [clojure.test :refer :all]
            [rksm.repl.utils :refer [lein search-for-symbol]]))

(deftest lein-test
  (testing "lein help"
    (is (.startsWith
         (with-out-str (lein help))
         "Leiningen is a tool"))))

(deftest search-for-symbol-test

  (testing "Find in default namespaces with re"
    (is (=
         (search-for-symbol #".*re-match.*")
         '("clojure.core/re-matcher ([re s])"
           "clojure.core/re-matches ([re s])"))))

  (testing "Find in default namespaces with string"
    (is (=
         (search-for-symbol "re-match")
         '("clojure.core/re-matcher ([re s])"
           "clojure.core/re-matches ([re s])"))))

  (testing "Find in default namespaces with multiple strings that must all match"
    (is (=
         (search-for-symbol #".*/re-.*" "matcher")
         '("clojure.core/re-matcher ([re s])")))))
