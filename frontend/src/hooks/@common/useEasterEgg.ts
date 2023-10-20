import { useState } from 'react';

import { useToast } from '@/context/Toast/ToastContext';

const AWAKE_NUMBER = 10;
const DANGER_NUMBER = AWAKE_NUMBER - 1;

let startTime: Date | undefined;
let endTime: Date | undefined;

const calcDuration = () => (Number(endTime) - Number(startTime)) / 1000;

const useEasterEgg = () => {
  const { toast } = useToast();

  const [dogTouchCount, setDogTouchCount] = useState(1);
  const isDogAwake = dogTouchCount > AWAKE_NUMBER;

  const onTouchDog = () => {
    if (dogTouchCount === AWAKE_NUMBER && startTime) {
      endTime = new Date();

      toast.success(`${calcDuration()}초 만에 강아지를 깨웠어요!`);
    } else if (dogTouchCount === DANGER_NUMBER) {
      toast.warning('강아지를 깨우지 않게 조심하세요!');
    } else if (dogTouchCount < AWAKE_NUMBER) {
      startTime = startTime ?? new Date();

      toast.info(`강아지를 ${dogTouchCount}번 쓰다듬었어요.`);
    }

    if (dogTouchCount <= AWAKE_NUMBER) setDogTouchCount(prev => prev + 1);
  };

  return { onTouchDog, isDogAwake };
};

export default useEasterEgg;
