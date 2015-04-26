(ns visualise-payments
    (:require
      [cl-java-introspector.spring :refer :all]
      [cl-java-introspector.core :refer :all]
      [me.raynes.fs :refer :all]
      [vinyasa.pull :refer [pull]]))

(pull 'incanter)

(def paymentService (get-bean "paymentService"))

(def reportingService (get-bean "reportingService"))

(defn pay!
  [cc-number]
  (.doPayment paymentService cc-number))
