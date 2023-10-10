import { useState } from 'react';
import { styled } from 'styled-components';

import ResearchIcon from '@/assets/svg/research_icon.svg';
import ToolTipButton from '@/assets/svg/tool_tip_btn.svg';
import VetIcon from '@/assets/svg/vet_icon.svg';
import ToolTip from '@/components/@common/ToolTip/ToolTip';
import { Brand } from '@/types/food/client';

const USA_STATE_IMG_URL =
  'data:image/png;base64,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';
const CANADA_STATE_IMG_URL =
  'https://media.istockphoto.com/id/934017954/ko/%EB%B2%A1%ED%84%B0/%EC%BA%90%EB%82%98%EB%8B%A4-flag.jpg?s=612x612&w=0&k=20&c=Bzikss7IgenHP791GUGSdNCaansgET67HRJxc3UgXrk=';

interface BrandBlockProps extends Brand {}

const BrandBlock = (brandBlockProps: BrandBlockProps) => {
  const { name, imageUrl, state, foundedYear, hasResidentVet, hasResearchCenter } = brandBlockProps;

  const [onFoundedYearTip, setOnFoundedYearTip] = useState<boolean>(false);
  const [onStateTip, setOnStateTip] = useState<boolean>(false);
  const [onVetTip, setOnVetTip] = useState<boolean>(false);
  const [onResearchCenterTip, setOnResearchCenterTip] = useState<boolean>(false);

  const onToolTip = (toolTipType: 'foundedYear' | 'state' | 'vet' | 'reserachCenter') => {
    setOnFoundedYearTip(toolTipType === 'foundedYear' && !onFoundedYearTip);
    setOnStateTip(toolTipType === 'state' && !onStateTip);
    setOnVetTip(toolTipType === 'vet' && !onVetTip);
    setOnResearchCenterTip(toolTipType === 'reserachCenter' && !onResearchCenterTip);
  };

  return (
    <BrandBlockWrapper>
      <BrandProfile>
        <BrandImage src={imageUrl} alt={name} />
        <BrandInfo>
          <BrandName>{name}</BrandName>
          <BrandFoundedYearButton onClick={() => onToolTip('foundedYear')}>
            <BrandFoundedYear>
              {`창립연도 ${foundedYear}년`}
              <ToolTipIcon src={ToolTipButton} alt="창립연도가 필요한 이유" />
            </BrandFoundedYear>
            {onFoundedYearTip && (
              <ToolTip
                showBubbleOnly
                content="반려동물 업계는 사료 품질과 판매량간의 상관관계가 없는데요, 마케팅 만으로는 살아남을 수 없기 때문에 역사가 오래됐는지가 중요한 지표랍니다!"
                title="왜 창립연도를 확인해야 하나요?"
                left="-12rem"
                edgeLeft="16rem"
              />
            )}
          </BrandFoundedYearButton>
        </BrandInfo>
      </BrandProfile>
      <BrandPersonalityList>
        <BrandPersonality type="button" onClick={() => onToolTip('state')}>
          {onStateTip && (
            <ToolTip
              showBubbleOnly
              content="반려동물 선진국의 경우, 관련 법규가 다른 나라에 비해 엄격한 편입니다. 그렇기에 제조사의 국가 역시 중요한 정보 중 하나랍니다!"
              title="왜 브랜드 국가를 확인해야 하나요?"
              left="-6rem"
              edgeLeft="5.1rem"
              direction="top"
            />
          )}
          <BrandPersonalityImg
            src={state === '미국' ? USA_STATE_IMG_URL : CANADA_STATE_IMG_URL}
            alt={`${state} 브랜드`}
          />
          <BrandPersonalityText>
            {state}
            <ToolTipIcon src={ToolTipButton} alt="브랜드 국가를 확인해야 하는 이유" />
          </BrandPersonalityText>
        </BrandPersonality>
        {hasResidentVet && (
          <BrandPersonality type="button" onClick={() => onToolTip('vet')}>
            {onVetTip && (
              <ToolTip
                showBubbleOnly
                content="전문가의 자문을 받고 판매를 하겠지만, 상주하는 전문가가 아닌 이상 먹거리에 대한 책임감이 차이가 날 수 밖에 없어서 중요한 지표중 하나랍니다!"
                title="왜 수의사 상주 여부가 중요한가요?"
                direction="top"
              />
            )}
            <BrandPersonalityImg src={VetIcon} alt="수의사 상주 여부" />
            <BrandPersonalityText>
              수의사 상주
              <ToolTipIcon src={ToolTipButton} alt="수의사 상주 여부가 중요한 이유" />
            </BrandPersonalityText>
          </BrandPersonality>
        )}
        {hasResearchCenter && (
          <BrandPersonality type="button" onClick={() => onToolTip('reserachCenter')}>
            {onResearchCenterTip && (
              <ToolTip
                showBubbleOnly
                content="브랜드에 전용 연구센터가 있다는 것은 사후 AS에서도 단순히 교환 처리나 보상 처리를 하는 것이 아니라 안전성 검사, 독성 검사, 영양소 분석을 통해 문제의 원인까지 면밀히 파악해서 재발을 방지할 수 있는 능력까지 갖췄다는 것을 의미해요!"
                title="왜 연구센터 여부가 중요한가요?"
                left="-24.5rem"
                edgeLeft="23.5rem"
                direction="top"
              />
            )}
            <BrandPersonalityImg src={ResearchIcon} alt="연구센터 여부" />
            <BrandPersonalityText>
              연구센터
              <ToolTipIcon src={ToolTipButton} alt="연구센터 여부가 중요한 이유" />
            </BrandPersonalityText>
          </BrandPersonality>
        )}
      </BrandPersonalityList>
    </BrandBlockWrapper>
  );
};

export default BrandBlock;

const BrandBlockWrapper = styled.div`
  padding: 2rem;

  background-color: ${({ theme }) => theme.color.grey200};
  border-radius: 20px;
`;

const BrandProfile = styled.div`
  display: flex;
  gap: 2rem;
  align-items: center;

  padding-bottom: 2rem;

  border-bottom: 1px solid ${({ theme }) => theme.color.grey250};
`;

const BrandImage = styled.img`
  width: 9rem;
  height: 9rem;

  object-fit: cover;
  border-radius: 20px;
`;

const BrandInfo = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.4rem;
`;

const BrandName = styled.h3`
  font-size: 2rem;
  font-weight: 700;
  line-height: 1.7rem;
  color: ${({ theme }) => theme.color.grey500};
  letter-spacing: -0.05rem;
`;

const BrandFoundedYearButton = styled.button`
  position: relative;

  border: none;
`;

const BrandFoundedYear = styled.p`
  display: flex;
  gap: 0.4rem;
  align-items: center;

  font-size: 1.4rem;
  font-weight: 500;
  line-height: 1.7rem;
  color: ${({ theme }) => theme.color.grey400};
  letter-spacing: -0.05rem;
`;

const BrandPersonalityList = styled.div`
  display: flex;
  gap: 4.8rem;
  justify-content: center;

  padding: 2rem 0 0;
`;

const BrandPersonality = styled.button`
  position: relative;

  display: flex;
  flex-direction: column;
  gap: 0.8rem;
  align-items: center;

  background-color: transparent;
  border: none;
`;

const BrandPersonalityImg = styled.img`
  width: 5.5rem;
  height: 5.5rem;

  object-fit: cover;
  border-radius: 10px;
`;

const BrandPersonalityText = styled.p`
  display: flex;
  gap: 0.4rem;
  align-items: center;

  font-size: 1.2rem;
  font-weight: 500;
  line-height: 1.7rem;
  color: ${({ theme }) => theme.color.grey400};
  letter-spacing: -0.05rem;
`;

const ToolTipIcon = styled.img`
  width: 1.6rem;
  height: 1.6rem;
`;
