import React, { useState } from 'react';

interface ButtonProps {
  /**
   * Is this the principal call to action on the page?
   */
  primary?: boolean;
  /**
   * What background color to use
   */
  backgroundColor?: string;
  /**
   * How large should the button be?
   */
  size?: 'small' | 'medium' | 'large';
  /**
   * Button contents
   */
  label: string;
  /**
   * Optional click handler
   */
  onClick?: () => void;
}

/**
 * Primary UI component for user interaction
 */

interface Todo {
  userId: 1;
  id: 1;
  title: string;
  completed: false;
}

/** `Button` 컴포넌트는 어떠한 작업을 트리거 할 때 사용합니다.  */
export const Button: React.FC<ButtonProps & Partial<Todo>> = ({
  primary = false,
  size = 'medium',
  backgroundColor,
  label,
  onClick,
  ...props
}) => {
  const [first, setfirst] = useState('');
  const mode = primary ? 'storybook-button--primary' : 'storybook-button--secondary';

  return (
    <>
      <button
        type="button"
        className={[
          'storybook-button',
          `storybook-button--${size}`,
          mode,
          'btn',
          'text-3xl',
          'font-bold',
          'underline',
        ].join(' ')}
        style={{ backgroundColor }}
      >
        {props.title ? props.title : label}
      </button>
      <div>Hello World</div>
      <form>
        <input data-testid="email" value={first} onChange={e => setfirst(e.target.value)} />
        <button type="button">btn</button>
      </form>
      <p>
        Nulla dolosr velit adipisicing duis excepteur esse in duis nostrud occaecat mollit
        incididsudeserunt sunt. Ut ut sunt laborum ex occaecat eu tempor labore enim adipisicing
        minim d. Est in quis eu dolorde occaecat excepteur fugiat dolore nisi aliqua fugiat enim
        utdd quis ss deserunt exdfdsfs. Enim laboris dolor magna pariatur. Dolor et ad sint
        voluptate sunt elit mollit officia ad enim sit consectetur enim.
      </p>
    </>
  );
};

export const ButtonWithHooks = () => {
  // Sets the hooks for both the label and primary props
  const [value, setValue] = useState('Secondary');
  const [isPrimary, setIsPrimary] = useState(false);

  // Sets a click handler to change the label's value
  const handleOnChange = () => {
    if (!isPrimary) {
      setIsPrimary(true);
      setValue('Primary');
    }
  };
  return <Button primary={isPrimary} onClick={handleOnChange} label={value} />;
};
