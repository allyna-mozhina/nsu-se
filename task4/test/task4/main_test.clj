(ns task4.main-test
  (:require [clojure.test :refer :all]
            [task4.main :refer :all])
  (:use clojure.data))

(deftest generate-without-doubles-test
  (testing "Testing permutation generation without doubles"
    (is (= nil (first 
      (diff (generate-without-doubles 3 '(a b))
            (list '(a b a) '(b a b))))))
    (is (= nil (first 
      (diff (generate-without-doubles 2 '(a b c))
            (list '(b a) '(c a) '(a b) '(c b) '(a c) '(b c))))))
    (is (= nil (first
      (diff (generate-without-doubles 2 '(1 2 3 4))
            (list '(2 1) '(3 1) '(4 1) '(1 2) '(3 2) '(4 2) 
                  '(1 3) '(2 3) '(4 3) '(1 4) '(2 4) '(3 4))))))))
