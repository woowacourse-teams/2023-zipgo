import styled from 'styled-components';

import AlignControlsIcon from '@/assets/svg/align_controls.svg';
import Select from '@/components/@common/Select/Select';
import useEasyNavigate from '@/hooks/@common/useEasyNavigate';
import { useReviewListAlignMeta } from '@/hooks/query/review';
import { generateQueryString } from '@/router/routes';

const AlignSelect = () => {
  const { metaData } = useReviewListAlignMeta();
  const { updateQueryString } = useEasyNavigate();

  if (!metaData) return null;

  const onValueChange = (value: string) => {
    const valueId = metaData.find(({ name }) => name === value)?.id;

    if (!valueId) throw new Error('Invalid align value');

    updateQueryString(generateQueryString({ sortBy: valueId }));
  };

  return (
    <Select defaultValue={metaData[0].name} onValueChange={onValueChange}>
      <Select.Trigger asChild>
        {({ selectedValue }) => (
          <Trigger>
            <TriggerIcon src={AlignControlsIcon} alt="리뷰 정렬 아이콘" />
            <span>{selectedValue || '정렬'}</span>
          </Trigger>
        )}
      </Select.Trigger>
      <Select.Content asChild>
        <Content>
          {metaData.map(({ id, name }) => (
            <Select.Item key={id} value={name} asChild>
              {({ selected }) => (
                <Item>
                  {selected && <Indicator>✔</Indicator>}
                  {name}
                </Item>
              )}
            </Select.Item>
          ))}
        </Content>
      </Select.Content>
    </Select>
  );
};

export default AlignSelect;

const Trigger = styled.button`
  display: flex;
  gap: 0.4rem;
  align-items: center;
  justify-content: center;

  min-height: 1.4rem;
  padding: 0.8rem;

  background: ${({ theme }) => theme.color.grey200};
  border: none;
  border-radius: 4px;

  & > span {
    padding-top: 0.2rem;

    font-size: 1.3rem;
    font-weight: 500;
    font-style: normal;
    color: ${({ theme }) => theme.color.grey400};
  }
`;

const Content = styled.div`
  position: absolute;
  z-index: 10000;
  transform: translateY(35px);

  filter: drop-shadow(0 4px 34px rgb(0 0 0 / 15%));
  backdrop-filter: blur(5px);
  border-radius: 8px;
`;

const Item = styled.div`
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

  &:hover {
    background-color: #ebebeb;
  }

  &:not(:last-child) {
    border-bottom: 1px solid ${({ theme }) => theme.color.grey250};
  }
`;

const TriggerIcon = styled.img`
  width: 1.4rem;
  height: 1.4rem;
`;

const Indicator = styled.span`
  position: absolute;
  left: 0.6rem;

  font-size: 1rem;
`;
