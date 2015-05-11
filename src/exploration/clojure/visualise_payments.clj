(ns visualise-payments
    (:require
      [cl-java-introspector.spring :refer :all]
      [cl-java-introspector.core :refer :all]
      [vinyasa.pull :refer [pull]]))

; Get the Spring service bean we want to test drive

(def paymentService (get-bean "paymentService"))

; Helpers for working with RxJava observables and Java beans

(defn- result-from-observable
  [observable]
  (.first (.toBlocking observable)))

(defn- sum-from-result
  [result]
  (.getSum (.getPayment result)))

(defn- do-test-payments
  "Performs n test payments, returning a map of results."
  [n]
  (letfn [(pay [cc-number]
            (result-from-observable (.doPayment paymentService cc-number)))]
    (map pay (repeat n "TEST_NUMBER"))))

(def payments (do-test-payments 500))

; for data analysis and graphs
(pull 'incanter)

(require
  '[incanter.core :as i]
  '[incanter.charts :as c])

; pull statuses and sums into an Incanter dataset
(def payments-dataset (i/dataset [:status :sum] (map (juxt :status sum-from-result) payments)))

; plot a histogram with the sums
(comment
  (i/view (c/histogram (i/sel payments-dataset :cols :sum) :nbins 20)))

