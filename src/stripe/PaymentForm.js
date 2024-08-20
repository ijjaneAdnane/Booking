          import { CardElement, useElements, useStripe } from '@stripe/react-stripe-js';
          import React, { useState } from 'react';
          import ApiService from '../service/ApiService';
          import './PaymentForm.css';

          const PaymentForm = ({ bookingDetails, roomId }) => {
            const [paymentType, setPaymentType] = useState('standard');
            const [success, setSuccess] = useState(false);
            const stripe = useStripe();
            const elements = useElements();

            const handleSubmit = async (event) => {
              event.preventDefault();

              const { error, paymentMethod } = await stripe.createPaymentMethod({
                type: 'card',
                card: elements.getElement(CardElement),
              });

              if (!error) {
                try {
                  const { id } = paymentMethod;
                  const paymentInfo = {
                    amount: bookingDetails.amount,
                    currency: 'mad',
                    receiptEmail: bookingDetails.email,
                    paymentMethodId: id,
                    paymentType: paymentType,
                    roomId: roomId,
                    bookingCode: bookingDetails.bookingCode
                  };
                  
                  const response = await ApiService.createPaymentIntent(paymentInfo);

                  if (response.success) {
                    await ApiService.completePayment(localStorage.getItem('token'));
                    setSuccess(true);
                  }
                } catch (error) {
                  console.log("Erreur de paiement", error);
                }
              } else {
                console.log(error.message);
              }
            };

            return (
              <>
                {!success ? (
                  <form onSubmit={handleSubmit}>
                    <div className="FormGroup">
                      <label>Type de paiement:</label>
                      <select value={paymentType} onChange={(e) => setPaymentType(e.target.value)}>
                        <option value="standard">Standard</option>
                        <option value="premium">Premium</option>
                        <option value="medium">Medium</option>
                      </select>
                    </div>
                    <fieldset className="FormGroup">
                      <div className="FormRow">
                        <CardElement />
                      </div>
                    </fieldset>
                    <button type="submit">Payer</button>
                  </form>
                ) : (
                  <div>
                    <h2>Paiement réussi !</h2>
                  </div>
                )}
              </>
            );
          };

          export default PaymentForm;
