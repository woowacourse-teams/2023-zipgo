import { ChangeEvent } from 'react';
import styled from 'styled-components';

import useEasyNavigate from '@/hooks/@common/useEasyNavigate';
import useValidQueryString from '@/hooks/common/useValidQueryString';
import { useReviewListAlignMeta } from '@/hooks/query/review';
import { generateQueryString } from '@/router/routes';
import { RuntimeError } from '@/utils/errors';

const ALIGN_QUERY = 'sortBy';

const AlignSelect = () => {
  const { metaData } = useReviewListAlignMeta();
  const { updateQueryString } = useEasyNavigate();
  const { sortBy } = useValidQueryString([ALIGN_QUERY]);

  if (!metaData) return null;

  const alignReviewList = ({ target: { value } }: ChangeEvent<HTMLSelectElement>) => {
    const valueId = metaData.find(({ name }) => name === value)?.id;

    if (!valueId) throw new RuntimeError({ code: 'WRONG_QUERY_STRING' }, valueId);

    updateQueryString(generateQueryString({ [ALIGN_QUERY]: valueId }));
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

export default AlignSelect;

const Select = styled.select`
  min-height: 1.4rem;
  padding: 0.8rem;

  font-size: 1.3rem;
  font-weight: 500;
  font-style: normal;
  color: ${({ theme }) => theme.color.grey400};
  text-align: center;

  background-color: ${({ theme }) => theme.color.grey200};
  border: none;
  border-radius: 4px;
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
