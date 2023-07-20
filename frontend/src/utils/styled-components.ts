import { IStyledComponent, Runtime } from 'styled-components';
import { Interpolation, StyledComponentBrand } from 'styled-components/dist/types';

export const getComputedStyleOfSC = <R extends Runtime, Props extends object>(
  sc: StyledComponentBrand,
  prop: string,
) => {
  const regex = new RegExp(`^${prop}:`);

  const isIStyledComponent = (sc: StyledComponentBrand): sc is IStyledComponent<R, Props> =>
    'componentStyle' in sc;

  if (!isIStyledComponent(sc)) throw new Error('It is not styled components');

  const testProp = (target: Interpolation<Props>) =>
    target
      ?.toString()
      .split(';')
      .find((props: string) => regex.test(props));

  return testProp(sc.componentStyle.rules.find(rule => testProp(rule)))?.replace(`${prop}:`, '');
};
