import { ChangeEvent } from 'react';
import styled, { css } from 'styled-components';

import { REVIEW_ALIGN_QUERY } from '@/constants/review';
import useEasyNavigate from '@/hooks/@common/useEasyNavigate';
import useValidQueryString from '@/hooks/common/useValidQueryString';
import { useReviewListAlignMeta } from '@/hooks/query/review';
import { generateQueryString } from '@/router/routes';
import { StyledProps } from '@/types/common/utility';
import { RuntimeError } from '@/utils/errors';

const AlignSelect = () => {
  const { metaData } = useReviewListAlignMeta();
  const { updateQueryString } = useEasyNavigate();
  const { sortBy } = useValidQueryString([REVIEW_ALIGN_QUERY]);

  if (!metaData) return null;

  const alignReviewList = ({ target: { value } }: ChangeEvent<HTMLSelectElement>) => {
    const valueId = metaData.find(({ name }) => name === value)?.id;

    if (!valueId) throw new RuntimeError({ code: 'WRONG_QUERY_STRING' }, valueId);

    updateQueryString(generateQueryString({ [REVIEW_ALIGN_QUERY]: valueId }));
  };

  return (
    <Select onChange={alignReviewList}>
      {metaData.map(({ id, name }) => (
        <Item key={id} value={name} selected={id === Number(sortBy)}>
          {name}
        </Item>
      ))}
    </Select>
  );
};

const Skeleton = () => <Select $skeleton />;

AlignSelect.Skeleton = Skeleton;

export default AlignSelect;

const Select = styled.select<StyledProps>`
  min-width: 9.5rem;
  min-height: 3.5rem;
  padding: 0.8rem;

  font-size: 1.3rem;
  font-weight: 500;
  font-style: normal;
  color: ${({ theme }) => theme.color.grey400};
  text-align: center;

  /* stylelint-disable-next-line CssSyntaxError */
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
  background-color: ${({ theme }) => theme.color.grey200};
  border: none;
  border-radius: 4px;

  ${({ theme, $skeleton }) =>
    $skeleton &&
    css`
      ${theme.animation.skeleton}
      appearance: none;
      border-radius: 4px;
    `};
`;

const Item = styled.option`
  cursor: pointer;

  position: relative;

  display: flex;
  gap: 1rem;
  align-items: center;

  min-width: 10rem;
  padding: 1rem 1rem 1rem 2rem;

  font-size: 1.3rem;
  font-weight: 500;
  font-style: normal;
  color: ${({ theme }) => theme.color.grey600};

  background-color: ${({ theme }) => theme.color.white};
`;
