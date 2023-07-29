import authHandlers from './auth';
import foodHandlers from './food';
import reviewHandlers from './review';

export default [...foodHandlers, ...authHandlers, ...reviewHandlers];
