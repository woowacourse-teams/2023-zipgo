import authHandlers from './auth';
import foodHandlers from './food';
import petProfileHandlers from './petProfile';
import reviewHandlers from './review';

export default [...foodHandlers, ...authHandlers, ...reviewHandlers, ...petProfileHandlers];
