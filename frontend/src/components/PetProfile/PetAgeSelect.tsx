import { ComponentPropsWithoutRef } from 'react';
import { styled } from 'styled-components';

interface PetAgeSelectProps extends ComponentPropsWithoutRef<'select'> {
  defaultAge?: number;
}

const PetAgeSelect = (petAgeSelectProps: PetAgeSelectProps) => {
  const { defaultAge, ...restProps } = petAgeSelectProps;

  return (
    <AgeSelect name="pet-age" aria-label="반려동물 나이 선택" {...restProps}>
      <option disabled selected={!defaultAge || Boolean(defaultAge < 0)}>
        여기를 눌러 아이의 나이를 선택해주세요.
      </option>
      <option value={0} selected={defaultAge === 0}>
        1살 미만
      </option>
      {Array.from({ length: 19 }, (_, index) => index + 1).map(age => (
        <option selected={age === defaultAge} key={`${age}살`} value={age}>{`${age}살`}</option>
      ))}
      <option value={20} selected={defaultAge === 20}>
        20살 이상
      </option>
    </AgeSelect>
  );
};

export default PetAgeSelect;

const AgeSelect = styled.select`
  cursor: pointer;

  width: 100%;
  padding: 1.2rem;

  font-size: 1.6rem;
  color: ${({ theme }) => theme.color.grey600};

  border: none;
  border-bottom: 1px solid ${({ theme }) => theme.color.grey400};
  outline: none;

  &:focus {
    border-color: #d0e6f9;
    border-width: 2px;
  }
`;
