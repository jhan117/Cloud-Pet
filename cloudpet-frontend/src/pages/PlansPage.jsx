import { useState } from "react";
import { useSearchParams } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSquarePlus } from "@fortawesome/free-regular-svg-icons";

import { useCarePlan } from "../contexts/CarePlanContext";
import Header from "../layouts/Header";
import Footer from "../layouts/Footer";
import CareCard from "../components/ui/CareCard";
import Card from "../components/ui/Card";
import FormModal from "../components/FormModal";

import classes from "./PlansPage.module.css";

const PlansPage = () => {
  const { carePlans, loading } = useCarePlan();
  const [searchParams, setSearchParams] = useSearchParams();
  const [editPlan, setEditPlan] = useState(null);
  const [showModal, setShowModal] = useState(false);

  const modalType = searchParams.get("modal");

  const openModal = (type, plan = null) => {
    setSearchParams({ modal: type });
    setEditPlan(plan);
    setShowModal(true);
  };

  const handleClose = () => {
    setSearchParams({});
    setShowModal(false);
    setEditPlan(null);
  };

  return (
    <>
      <Header />
      <main>
        <Card className={classes.plans}>
          <div className={classes.title}>
            <h2>Care 목록 관리</h2>
            <FontAwesomeIcon
              icon={faSquarePlus}
              onClick={() => openModal("add")}
            />
          </div>
          <div className={classes.careCards}>
            {loading ? (
              <p>Loading...</p>
            ) : (
              carePlans.map((plan) => (
                <CareCard
                  key={plan.planId}
                  plan={plan}
                  onEdit={() => openModal("edit", plan)}
                />
              ))
            )}
          </div>
        </Card>
        {showModal && (
          <FormModal
            mode={modalType}
            editPlan={editPlan}
            showModal={showModal}
            onClose={handleClose}
          />
        )}
      </main>
      <Footer />
    </>
  );
};

export default PlansPage;
