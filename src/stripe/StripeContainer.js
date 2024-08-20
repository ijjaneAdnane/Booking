import { Elements } from '@stripe/react-stripe-js';
import { loadStripe } from '@stripe/stripe-js';
import React from 'react';
import PaymentForm from './PaymentForm';

// Replace with your Stripe public key
const stripePromise = loadStripe('pk_test_51PYnpxKvoGy68e7ALUp7AQz4swL7zL0w9Zt63Phur3CnES9JksJMWBKGVXOLLrxWp5motRWzsblDSTW6Pl2HqZtm00K51LbTyT');

const StripeContainer = ({ bookingDetails, roomId }) => {
  return (
    <Elements stripe={stripePromise}>
      <PaymentForm bookingDetails={bookingDetails} roomId={roomId} />
    </Elements>
  );
};

export default StripeContainer;
