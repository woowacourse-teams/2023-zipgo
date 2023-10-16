import { styled } from 'styled-components';

import EUFlag from '@/assets/svg/flag_eu.svg';
import USFlag from '@/assets/svg/flag_us.svg';
import Label from '@/components/@common/Label/Label';
import theme from '@/styles/theme';
import { State } from '@/types/food/client';

interface NutritionStandardBlockProps {
  state: State;
  satisfied: boolean;
}

const NutritionStandardBlock = (nutritionStandardBlockProps: NutritionStandardBlockProps) => {
  const { state, satisfied } = nutritionStandardBlockProps;

  const isUs = state === State.us;

  return (
    <NutritionStandardBlockWrapper>
      <StandardInfo>
        <CountryFlag src={isUs ? USFlag : EUFlag} alt={isUs ? State.us : State.eu} />
        <CountryName>{state}</CountryName>
      </StandardInfo>
      <Label
        text={satisfied ? '충족' : '불충족'}
        hasBorder={false}
        backgroundColor={satisfied ? theme.color.blue : theme.color.lightRed}
        textColor={satisfied ? theme.color.primary : theme.color.warning}
        width={6}
        fontSize={1.3}
        fontWeight={700}
      />
    </NutritionStandardBlockWrapper>
  );
};

export default NutritionStandardBlock;

const NutritionStandardBlockWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 100%;
  padding: 1.6rem 2.4rem;

  background-color: ${({ theme }) => theme.color.grey200};
  border-radius: 15px;
`;

const StandardInfo = styled.div`
  display: flex;
  gap: 1.6rem;
  align-items: center;
`;

const CountryFlag = styled.img`
  width: 4rem;
  height: 4rem;
`;

const CountryName = styled.h3`
  font-size: 1.6rem;
  font-weight: 700;
  line-height: 1.7rem;
  color: ${({ theme }) => theme.color.grey700};
  letter-spacing: -0.05rem;
`;
