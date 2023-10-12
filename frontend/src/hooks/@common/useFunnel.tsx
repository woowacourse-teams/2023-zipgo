import { useMemo, useState } from 'react';

import { Funnel, FunnelProps, Step } from '@/components/@common/Funnel/Funnel';
import { NonEmptyArray } from '@/types/common/utility';

export const useFunnel = <Steps extends NonEmptyArray<string>>(
  steps: Steps,
  defaultStep: Steps[number],
) => {
  const [step, setStep] = useState<Steps[number]>(defaultStep);

  const FunnelComponent = useMemo(
    () =>
      Object.assign(
        (props: Omit<FunnelProps<Steps>, 'step' | 'steps'>) => (
          <Funnel<Steps> step={step} steps={steps} {...props} />
        ),
        { Step },
      ),
    [step],
  );

  return [FunnelComponent, setStep] as const;
};
