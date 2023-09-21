import { useEffect } from 'react';
import styled, { css } from 'styled-components';

import FilterControlsIcon from '@/assets/svg/filter_controls.svg';
import { Dialog } from '@/components/@common/Dialog/Dialog';
import useEasyNavigate from '@/hooks/@common/useEasyNavigate';
import { useReviewListFilterMeta } from '@/hooks/query/review';
import useReviewFilterList from '@/hooks/review/useReviewFilterList';
import { generateQueryString } from '@/router/routes';

const FilterDialog = () => {
  const { metaData } = useReviewListFilterMeta();
  const { replaceQueryString } = useEasyNavigate();

  const {
    filterList: { petSizes, ageGroups, breeds },
    parsedFilterList,
    toggleFilter,
    onSelectBreed,
    resetFilterList,
  } = useReviewFilterList();

  const queryString = generateQueryString(parsedFilterList);

  const confirm = () => replaceQueryString(queryString, { exclude: ['sortBy'] });

  useEffect(() => () => resetFilterList(), []);

  if (!metaData) return null;

  return (
    <Dialog>
      <Dialog.Trigger asChild>
        <DialogTrigger type="button">
          <TriggerIcon src={FilterControlsIcon} alt="리뷰 필터 아이콘" />
          <span>필터</span>
        </DialogTrigger>
      </Dialog.Trigger>
      <Dialog.Portal>
        <Dialog.BackDrop />
        <Dialog.Content asChild>
          <DialogContentLayout>
            <TopContainer>
              <DialogTitle>리뷰 검색 필터</DialogTitle>
              <Dialog.Close asChild>
                <Close>𝖷</Close>
              </Dialog.Close>
            </TopContainer>
            <FilterContainer>
              <FilterSection>
                <FilterTitle>크기</FilterTitle>
                <FilterList>
                  {metaData.petSizes.map(({ id, name }) => (
                    <FilterItem
                      role="checkbox"
                      key={id}
                      aria-checked={petSizes.has(id)}
                      $selected={petSizes.has(id)}
                      onClick={() => toggleFilter('petSizes', id)}
                    >
                      {name}
                    </FilterItem>
                  ))}
                </FilterList>
              </FilterSection>
              <FilterSection>
                <FilterTitle>나이대</FilterTitle>
                <FilterList>
                  {metaData.ageGroups.map(({ id, name }) => (
                    <FilterItem
                      role="checkbox"
                      key={id}
                      aria-checked={ageGroups.has(id)}
                      $selected={ageGroups.has(id)}
                      onClick={() => toggleFilter('ageGroups', id)}
                    >
                      {name}
                    </FilterItem>
                  ))}
                </FilterList>
              </FilterSection>
              <FilterSection>
                <FilterTitle>견종</FilterTitle>
                <Select onChange={onSelectBreed}>
                  <option value={-1} selected={breeds.has(-1)}>
                    선택 안 함
                  </option>
                  {metaData.breeds.map(({ id, name }) => (
                    <option key={id} value={id} selected={breeds.has(id)}>
                      {name}
                    </option>
                  ))}
                </Select>
              </FilterSection>
            </FilterContainer>
            <ButtonContainer>
              <ResetButton type="button" onClick={resetFilterList}>
                초기화
              </ResetButton>
              <Dialog.Close asChild onClick={confirm}>
                <ConfirmButton type="button">적용하기 ＞</ConfirmButton>
              </Dialog.Close>
            </ButtonContainer>
          </DialogContentLayout>
        </Dialog.Content>
      </Dialog.Portal>
    </Dialog>
  );
};

export default FilterDialog;

const DialogTrigger = styled.button`
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

const TriggerIcon = styled.img`
  width: 1.4rem;
  height: 1.4rem;
`;

const DialogContentLayout = styled.div`
  position: fixed;
  z-index: 1001;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  display: flex;
  flex-direction: column;
  gap: 2rem;
  align-items: flex-start;
  justify-content: center;

  width: 33.8rem;
  padding: 2rem;

  background: ${({ theme }) => theme.color.white};
  border-radius: 20px;
  box-shadow: 0 0 10px 14px rgb(0 0 0 / 15%);
`;

const TopContainer = styled.div`
  display: flex;
  justify-content: space-between;

  width: 100%;
`;

const DialogTitle = styled.h1`
  font-size: 1.4rem;
  font-weight: 500;
  font-style: normal;
  color: ${({ theme }) => theme.color.grey400};
  text-align: center;
`;

const Close = styled.span`
  cursor: pointer;

  font-size: 1.3rem;
`;

const FilterContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;

  width: 100%;
`;

const FilterSection = styled.section`
  display: flex;
  flex-direction: column;
  gap: 1.2rem;

  width: 100%;
`;

const FilterTitle = styled.h2`
  font-size: 2rem;
  font-weight: 700;
  font-style: normal;
  line-height: normal;
  color: ${({ theme }) => theme.color.grey600};
`;

const FilterList = styled.ul`
  display: flex;
  flex-wrap: wrap;
  gap: 0.8rem;
`;

const FilterItem = styled.li<{
  $selected: boolean;
}>`
  cursor: pointer;

  display: flex;
  gap: 0.4rem;
  align-items: center;

  padding: 1rem 1.6rem;

  font-size: 1.4rem;
  font-weight: 500;
  font-style: normal;
  color: ${({ theme }) => theme.color.primary};
  letter-spacing: 0.4px;
  list-style: none;

  background-color: transparent;
  border: 1px solid ${({ theme }) => theme.color.primary};
  border-radius: 20px;

  transition: background-color 0.2s, border 0.2s;

  ${({ $selected }) =>
    $selected &&
    css`
      background-color: #d0e6f9;
      border: 1px solid #d0e6f9;
    `};
`;

const Select = styled.select`
  width: 100%;
  height: 4.1rem;

  font-size: 1.8rem;
  font-weight: 500;
  font-style: normal;
  color: ${({ theme }) => theme.color.grey400};
  text-align: center;

  border: 1px solid ${({ theme }) => theme.color.grey400};
  border-radius: 16px;
`;

const ConfirmButton = styled.button`
  width: 70%;
  margin: 0 auto;
  padding: 1.6rem 2rem;

  font-size: 1.6rem;
  font-weight: 500;
  font-style: normal;
  line-height: 2rem;
  color: ${({ theme }) => theme.color.white};

  background: ${({ theme }) => theme.color.primary};
  border: none;
  border-radius: 16px;

  transition: transform 0.2s;

  &:hover {
    transform: scale(0.98);
  }

  &:active {
    transform: scale(0.93);
  }
`;

const ResetButton = styled.button`
  margin: 0 auto;
  padding: 1.6rem 2rem;

  font-size: 1.6rem;
  font-weight: 500;
  font-style: normal;
  line-height: 2rem;
  color: ${({ theme }) => theme.color.grey400};

  background-color: transparent;
  border: none;

  transition: transform 0.2s;

  &:hover {
    transform: scale(0.98);
  }

  &:active {
    transform: scale(0.95);
  }
`;

const ButtonContainer = styled.div`
  display: flex;
  align-items: center;

  width: 100%;
`;
