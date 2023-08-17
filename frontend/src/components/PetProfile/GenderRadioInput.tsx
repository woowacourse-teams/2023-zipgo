import { InputHTMLAttributes } from 'react';
import { css, styled } from 'styled-components';

import FemaleIconDark from '@/assets/svg/female_icon_dark.svg';
import FemaleIconLight from '@/assets/svg/female_icon_light.svg';
import MaleIconDark from '@/assets/svg/male_icon_dark.svg';
import MaleIconLight from '@/assets/svg/male_icon_light.svg';
import { FEMALE, MALE } from '@/constants/petProfile';
import { Gender } from '@/types/petProfile/client';

interface GenderRadioInputProps extends InputHTMLAttributes<HTMLInputElement> {
  text?: string;
  gender?: Gender;
}

const GenderRadioInput = (genderRadioInputProps: GenderRadioInputProps) => {
  const { text, gender = MALE, ...restProps } = genderRadioInputProps;

  const getGenderIconImage = (gender: Gender, checked: boolean = false) => {
    if (gender === MALE && checked) return <img src={MaleIconLight} alt="" />;
    if (gender === MALE && !checked) return <img src={MaleIconDark} alt="" />;
    if (gender === FEMALE && checked) return <img src={FemaleIconLight} alt="" />;

    return <img src={FemaleIconDark} alt="" />;
  };

  return (
    <div>
      <GenderRadioInputWrapper
        type="radio"
        name="gender"
        value={gender}
        aria-label={`${gender}아`}
        {...restProps}
      />
      <ImageAndTextWrapper checked={restProps.checked}>
        {getGenderIconImage(gender, restProps.checked)}
        {text && <LabelText>{text}</LabelText>}
      </ImageAndTextWrapper>
    </div>
  );
};

export default GenderRadioInput;

const GenderRadioInputWrapper = styled.input<GenderRadioInputProps>`
  cursor: pointer;

  position: absolute;
  z-index: 1000;

  width: 16rem;
  height: 19.2rem;

  opacity: 0;
`;

const ImageAndTextWrapper = styled.div<{ checked?: boolean }>`
  cursor: pointer;

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  width: 16rem;
  height: 19.2rem;

  color: ${({ theme }) => theme.color.primary};

  background-color: ${({ theme }) => theme.color.white};
  border: none;
  border-radius: 15px;
  box-shadow: 0 4px 10px 3px rgb(0 0 0 / 15%);

  ${({ checked }) =>
    checked &&
    css`
      color: ${({ theme }) => theme.color.white};

      background-color: ${({ theme }) => theme.color.primary};
    `}
`;

const LabelText = styled.p`
  margin-top: 0.8rem;

  font-size: 2rem;
  font-weight: 700;
  line-height: 1.7rem;
  color: inherit;
  text-align: center;
  letter-spacing: -0.5px;
`;
